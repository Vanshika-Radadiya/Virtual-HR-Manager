package com.vanshika.springboot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanshika.springboot.model.CompId;
import com.vanshika.springboot.model.InterviewAssignment;
import com.vanshika.springboot.model.InterviewDetails;
import com.vanshika.springboot.model.Interview;
import com.vanshika.springboot.repository.InterviewAssignmentRepo;
import com.vanshika.springboot.repository.InterviewDetailsRepo;
import com.vanshika.springboot.repository.InterviewRepo;
import com.vanshika.springboot.model.User;
import com.vanshika.springboot.repository.UserRepo;

@Service
public class InterviewService {
	@Autowired
	InterviewRepo interviewRepo;
	@Autowired
	InterviewAssignmentRepo assignmentRepo;
	@Autowired
	InterviewDetailsRepo interviewDetailsRepo;
	
	public List<Interview> getInterviewByUserId(int userId){
		return interviewRepo.getInterviewByUserId(userId);
	}
	
	public List<Interview> getUpcoming(int userId){
		List<Interview> upcoming = new ArrayList<>();
		for (InterviewAssignment assignment : assignmentRepo.findByUserIdOrderByStartDateDesc(userId)) {
			InterviewDetails details = interviewDetailsRepo.findById(
					new CompId(userId, assignment.getInterviewId())).orElse(null);
			if (details != null && details.getEndDate() != null) {
				continue;
			}

			Interview interview = interviewRepo.findById(assignment.getInterviewId()).orElse(null);
			if (interview != null) {
				upcoming.add(interview);
			}
		}
		return upcoming;
	}
	
	public List<Interview> getPast(int userId){
		List<Interview> past = new ArrayList<>();
		for (InterviewAssignment assignment : assignmentRepo.findByUserIdOrderByStartDateDesc(userId)) {
			InterviewDetails details = interviewDetailsRepo.findById(
					new CompId(userId, assignment.getInterviewId())).orElse(null);
			if (details == null || details.getEndDate() == null) {
				continue;
			}

			Interview interview = interviewRepo.findById(assignment.getInterviewId()).orElse(null);
			if (interview != null) {
				interview.setEndDate(details.getEndDate());
				past.add(interview);
			}
		}
		return past;
	}
	
	public Interview getRoleByInterviewId(int interviewId) {
		return interviewRepo.getRoleByInterviewId(interviewId);
	}
}
