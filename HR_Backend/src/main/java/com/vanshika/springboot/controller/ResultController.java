package com.vanshika.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vanshika.springboot.model.Interview;
import com.vanshika.springboot.model.Result;
import com.vanshika.springboot.model.User;
import com.vanshika.springboot.repository.UserRepo;
import com.vanshika.springboot.service.InterviewService;
import com.vanshika.springboot.service.ResultService;
import com.vanshika.springboot.service.UserService;

@RestController
public class ResultController {
	@Autowired
	ResultService resultService;
	
	@GetMapping("/result/userId/{userId}")
	public List<Result> getPast(@PathVariable int userId){
		return resultService.getResultByUserId(userId);
	}
	
	
}
