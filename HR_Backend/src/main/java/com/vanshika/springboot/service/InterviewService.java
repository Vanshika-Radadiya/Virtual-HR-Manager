package com.vanshika.springboot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanshika.springboot.model.Interview;
import com.vanshika.springboot.model.User;
import com.vanshika.springboot.repository.InterviewRepo;
import com.vanshika.springboot.repository.UserRepo;

@Service
public class InterviewService {
	@Autowired
	InterviewRepo interviewRepo;
	
	public List<Interview> getInterviewByUserId(int userId){
		return interviewRepo.getInterviewByUserId(userId);
	}
	
	public List<Interview> getUpcoming(int userId){
		List<Interview> list = interviewRepo.getInterviewByUserId(userId);
		LocalDateTime curr = LocalDateTime.now();
		List<Interview> upcoming = new ArrayList<Interview>();
		for(Interview it: list) {
			if(it.getEndDate().isAfter(curr)) {
				upcoming.add(it);
			}
		}
		return upcoming;
	}
	
	public List<Interview> getPast(int userId){
		List<Interview> list = interviewRepo.getInterviewByUserId(userId);
		LocalDateTime curr = LocalDateTime.now();
		List<Interview> past = new ArrayList<Interview>();
		for(Interview it: list) {
			if(it.getEndDate().isBefore(curr)) {
				past.add(it);
			}
		}
		return past;
	}
	
	public Interview getRoleByInterviewId(int interviewId) {
		return interviewRepo.getRoleByInterviewId(interviewId);
	}
}
