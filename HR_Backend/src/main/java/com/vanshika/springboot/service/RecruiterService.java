package com.vanshika.springboot.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vanshika.springboot.model.CandidateScoreResponse;
import com.vanshika.springboot.model.CreateInterviewRequest;
import com.vanshika.springboot.model.EmailDetails;
import com.vanshika.springboot.model.Interview;
import com.vanshika.springboot.model.InterviewAssignment;
import com.vanshika.springboot.model.InterviewDetails;
import com.vanshika.springboot.model.Record;
import com.vanshika.springboot.model.Result;
import com.vanshika.springboot.model.CompId;
import com.vanshika.springboot.model.User;
import com.vanshika.springboot.repository.InterviewAssignmentRepo;
import com.vanshika.springboot.repository.InterviewDetailsRepo;
import com.vanshika.springboot.repository.InterviewRepo;
import com.vanshika.springboot.repository.RecordRepo;
import com.vanshika.springboot.repository.ResultRepo;
import com.vanshika.springboot.repository.UserRepo;

@Service
public class RecruiterService {
	private static final Pattern EMAIL_PATTERN =
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);
	private static final String ALPHANUMERIC =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final DateTimeFormatter INVITATION_DATE =
			DateTimeFormatter.ofPattern("EEE, d MMM yyyy 'at' h:mm a", Locale.ENGLISH);
	private static final SecureRandom RANDOM = new SecureRandom();

	private final InterviewRepo interviewRepo;
	private final InterviewAssignmentRepo assignmentRepo;
	private final InterviewDetailsRepo interviewDetailsRepo;
	private final UserRepo userRepo;
	private final ResultRepo resultRepo;
	private final RecordRepo recordRepo;
	private final EmailService emailService;
	private final String frontendUrl;

	public RecruiterService(InterviewRepo interviewRepo, InterviewAssignmentRepo assignmentRepo,
			InterviewDetailsRepo interviewDetailsRepo,
			UserRepo userRepo, ResultRepo resultRepo, RecordRepo recordRepo, EmailService emailService,
			@Value("${hr.frontend.url:http://localhost:4200}") String frontendUrl) {
		this.interviewRepo = interviewRepo;
		this.assignmentRepo = assignmentRepo;
		this.interviewDetailsRepo = interviewDetailsRepo;
		this.userRepo = userRepo;
		this.resultRepo = resultRepo;
		this.recordRepo = recordRepo;
		this.emailService = emailService;
		this.frontendUrl = frontendUrl;
	}

	public List<Interview> getInterviews(int recruiterId) {
		return interviewRepo.findByRecruiterIdOrderByStartDateDesc(recruiterId);
	}

	public List<CandidateScoreResponse> getCandidates(int recruiterId, int interviewId) {
		requireOwnedInterview(recruiterId, interviewId);
		List<CandidateScoreResponse> candidates = new ArrayList<>();
		for (InterviewAssignment assignment : assignmentRepo.findByInterviewIdOrderByUserIdAsc(interviewId)) {
			User candidate = userRepo.findById(assignment.getUserId())
					.orElseThrow(() -> new IllegalStateException("An assigned candidate account no longer exists."));
			Result result = resultRepo.getByCompId(new CompId(candidate.getUserId(), interviewId));
			int oops = result == null ? 0 : result.getOops();
			int os = result == null ? 0 : result.getOs();
			int dbms = result == null ? 0 : result.getDbms();
			candidates.add(new CandidateScoreResponse(candidate.getUserId(), candidate.getUsername(),
					candidate.getEmailId(), oops + os + dbms, result != null, assignment.isCompleted(),
					oops, os, dbms));
		}
		candidates.sort((left, right) -> {
			int scoreOrder = Integer.compare(right.getScore(), left.getScore());
			return scoreOrder != 0 ? scoreOrder : left.getUsername().compareToIgnoreCase(right.getUsername());
		});
		return candidates;
	}

	public List<Record> getAnswers(int recruiterId, int interviewId, int candidateId) {
		requireOwnedInterview(recruiterId, interviewId);
		if (!assignmentRepo.existsByInterviewIdAndUserId(interviewId, candidateId)) {
			throw new IllegalArgumentException("Candidate is not assigned to this interview.");
		}
		return recordRepo.findByInterviewIdAndUserIdOrderByRecordIdAsc(interviewId, candidateId);
	}

	@Transactional
	public Interview createInterview(int recruiterId, CreateInterviewRequest request) {
		validateInterviewRequest(request);
		List<String> emails = normalizeEmails(request.getEmailIds());
		if (emails.isEmpty()) {
			throw new IllegalArgumentException("Add at least one candidate email address.");
		}

		Interview interview = new Interview();
		interview.setRole(request.getRole().trim());
		interview.setDescription(request.getDescription() == null ? "" : request.getDescription().trim());
		interview.setStartDate(request.getStartDate());
		interview.setEndDate(request.getEndDate());
		interview.setRecruiterId(recruiterId);
		interview = interviewRepo.saveAndFlush(interview);

		for (String email : emails) {
			User candidate = findOrCreateCandidate(email);
			int interviewId = interview.getInterviewId();
			int userId = candidate.getUserId();

			// Save to interview_assignment
			assignmentRepo.save(new InterviewAssignment(interviewId, userId, interview.getStartDate()));

			// Save to interview_details
			InterviewDetails details = new InterviewDetails(interviewId, userId, interview.getStartDate(), interview.getEndDate());
			interviewDetailsRepo.save(details);

			sendInvitation(candidate, interview);
		}
		return interview;
	}

	public List<String> readCandidateEmails(MultipartFile file) throws IOException {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("Choose a non-empty .xlsx or .csv file.");
		}
		String filename = file.getOriginalFilename();
		if (filename == null) {
			throw new IllegalArgumentException("The uploaded file must have a .xlsx or .csv filename.");
		}
		String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase(Locale.ROOT);
		List<String> values = switch (extension) {
			case "xlsx" -> readSpreadsheet(file);
			case "csv" -> readCsv(file);
			default -> throw new IllegalArgumentException("Only .xlsx and .csv candidate files are supported.");
		};
		return normalizeEmails(values);
	}

	private List<String> readSpreadsheet(MultipartFile file) throws IOException {
		List<String> emails = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			if (workbook.getNumberOfSheets() == 0) {
				throw new IllegalArgumentException("The spreadsheet does not contain a worksheet.");
			}
			DataFormatter formatter = new DataFormatter();
			for (Row row : workbook.getSheetAt(0)) {
				String value = formatter.formatCellValue(row.getCell(0)).trim();
				if (!value.isEmpty() && !isEmailHeader(value)) {
					emails.add(value);
				}
			}
		}
		return emails;
	}

	private List<String> readCsv(MultipartFile file) throws IOException {
		List<String> emails = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			int lineNumber = 0;
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				String value = lineNumber == 1 ? line.replace("\uFEFF", "").trim() : line.trim();
				if (value.isEmpty() || isEmailHeader(value)) {
					continue;
				}
				if (value.startsWith("\"") && value.endsWith("\"") && value.length() >= 2) {
					value = value.substring(1, value.length() - 1).replace("\"\"", "\"").trim();
				}
				if (value.contains(",")) {
					throw new IllegalArgumentException("CSV row " + lineNumber
							+ " must contain one email address in its first column.");
				}
				emails.add(value);
			}
		}
		return emails;
	}

	private boolean isEmailHeader(String value) {
		String header = value.trim().replace("_", "").replace(" ", "").toLowerCase(Locale.ROOT);
		return header.equals("email") || header.equals("emailid") || header.equals("emailaddress");
	}

	private List<String> normalizeEmails(List<String> values) {
		if (values == null) {
			return List.of();
		}
		Set<String> emails = new LinkedHashSet<>();
		for (String value : values) {
			if (value == null || value.isBlank()) {
				continue;
			}
			String email = value.trim().toLowerCase(Locale.ROOT);
			if (!EMAIL_PATTERN.matcher(email).matches()) {
				throw new IllegalArgumentException("Invalid candidate email address: " + value);
			}
			emails.add(email);
		}
		return new ArrayList<>(emails);
	}

	private void validateInterviewRequest(CreateInterviewRequest request) {
		if (request == null || request.getRole() == null || request.getRole().isBlank()) {
			throw new IllegalArgumentException("Interview role is required.");
		}
		if (request.getStartDate() == null || request.getEndDate() == null
				|| !request.getEndDate().isAfter(request.getStartDate())) {
			throw new IllegalArgumentException("End time must be after the start time.");
		}
	}

	private User findOrCreateCandidate(String email) {
		User user = userRepo.findByEmailIdIgnoreCase(email).orElse(null);
		if (user != null) {
			if ("recruiter".equalsIgnoreCase(user.getRole())) {
				throw new IllegalArgumentException(email + " belongs to a recruiter account.");
			}
			return user;
		}

		user = new User();
		user.setEmailId(email);
		user.setRole("candidate");
		user.setUsername(generateUniqueUsername());
		String password = generateRandomCredential();
		user.setPassword(password);
		userRepo.saveAndFlush(user);
		user.setPassword(password);
		return user;
	}

	private String generateUniqueUsername() {
		String username;
		do {
			username = generateRandomCredential();
		} while (userRepo.existsByUsername(username));
		return username;
	}

	private String generateRandomCredential() {
		StringBuilder value = new StringBuilder(8);
		for (int index = 0; index < 8; index++) {
			value.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
		}
		return value.toString();
	}

	private void sendInvitation(User candidate, Interview interview) {
		String subject = "Your " + interview.getRole() + " interview is scheduled";
		String body = "Hello,\n\n"
				+ "You have been invited to a virtual interview.\n"
				+ "Role: " + interview.getRole() + "\n"
				+ "Details: " + interview.getDescription() + "\n"
				+ "Start: " + interview.getStartDate().format(INVITATION_DATE) + "\n"
				+ "End: " + interview.getEndDate().format(INVITATION_DATE) + "\n\n"
				+ "Sign in at " + frontendUrl + " with:\n"
				+ "Username: " + candidate.getUsername() + "\n"
				+ "Password: " + candidate.getPassword() + "\n\n"
				+ "Please sign in before your scheduled interview.";
		emailService.sendSimpleMail(new EmailDetails(candidate.getEmailId(), subject, body));
	}

	private void requireOwnedInterview(int recruiterId, int interviewId) {
		Interview interview = interviewRepo.findById(interviewId)
				.orElseThrow(() -> new IllegalArgumentException("Interview not found."));
		if (interview.getRecruiterId() == null || interview.getRecruiterId() != recruiterId) {
			throw new IllegalArgumentException("Interview not found.");
		}
	}
}
