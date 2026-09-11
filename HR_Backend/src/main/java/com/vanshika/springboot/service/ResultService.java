package com.vanshika.springboot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanshika.springboot.model.Interview;
import com.vanshika.springboot.model.Result;
import com.vanshika.springboot.model.User;
import com.vanshika.springboot.repository.InterviewRepo;
import com.vanshika.springboot.repository.ResultRepo;
import com.vanshika.springboot.repository.UserRepo;

@Service
public class ResultService {
	@Autowired
	ResultRepo resultRepo;
	
	public List<Result> getResultByUserId(int userId){
		return resultRepo.getResultByUserId(userId);
	}
	
	
}
