package com.vanshika.springboot.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vanshika.springboot.model.CandidateScoreResponse;
import com.vanshika.springboot.model.CreateInterviewRequest;
import com.vanshika.springboot.model.Interview;
import com.vanshika.springboot.model.Record;
import com.vanshika.springboot.service.RecruiterService;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
	private final RecruiterService recruiterService;

	public RecruiterController(RecruiterService recruiterService) {
		this.recruiterService = recruiterService;
	}

	@GetMapping("/interviews")
	public ResponseEntity<List<Interview>> getInterviews(HttpSession session) {
		Integer recruiterId = getRecruiterId(session);
		if (recruiterId == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok(recruiterService.getInterviews(recruiterId));
	}

	@GetMapping("/interviews/{interviewId}/candidates")
	public ResponseEntity<?> getCandidates(@PathVariable int interviewId, HttpSession session) {
		Integer recruiterId = getRecruiterId(session);
		if (recruiterId == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		try {
			return ResponseEntity.ok(recruiterService.getCandidates(recruiterId, interviewId));
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/interviews/{interviewId}/candidates/{candidateId}/answers")
	public ResponseEntity<?> getAnswers(@PathVariable int interviewId, @PathVariable int candidateId,
			HttpSession session) {
		Integer recruiterId = getRecruiterId(session);
		if (recruiterId == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		try {
			return ResponseEntity.ok(recruiterService.getAnswers(recruiterId, interviewId, candidateId));
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/interviews")
	public ResponseEntity<?> createInterview(@RequestBody CreateInterviewRequest request, HttpSession session) {
		Integer recruiterId = getRecruiterId(session);
		if (recruiterId == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		try {
			Interview interview = recruiterService.createInterview(recruiterId, request);
			return ResponseEntity.status(HttpStatus.CREATED).body(interview);
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.badRequest().body(Map.of("message", exception.getMessage()));
		}
	}

	@PostMapping("/import-candidates")
	public ResponseEntity<?> importCandidates(@RequestPart("file") MultipartFile file, HttpSession session)
			throws IOException {
		if (getRecruiterId(session) == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		try {
			return ResponseEntity.ok(recruiterService.readCandidateEmails(file));
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.badRequest().body(Map.of("message", exception.getMessage()));
		}
	}

	private Integer getRecruiterId(HttpSession session) {
		Object userId = session.getAttribute("userId");
		Object role = session.getAttribute("role");
		if (!(userId instanceof Integer id) || !(role instanceof String userRole)
				|| !"recruiter".equalsIgnoreCase(userRole)) {
			return null;
		}
		return id;
	}
}
