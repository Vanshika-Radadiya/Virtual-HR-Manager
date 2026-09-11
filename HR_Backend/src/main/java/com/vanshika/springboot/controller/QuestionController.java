package com.vanshika.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vanshika.springboot.model.AnswerResponse;
import com.vanshika.springboot.model.QuestionResponse;
import com.vanshika.springboot.model.User;
import com.vanshika.springboot.repository.UserRepo;
import com.vanshika.springboot.service.QuestionService;
import com.vanshika.springboot.service.UserService;

@RestController
public class QuestionController {
	@Autowired
	QuestionService questionService;
	
	@PostMapping("/saverecord")
	public ResponseEntity<QuestionResponse> generateNewQuestion(@RequestBody AnswerResponse ansResp){
		try {
			QuestionResponse Qr =  questionService.generateNewQuestion(ansResp);
//			System.out.println(Qr.getQ);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(Qr);
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

		}
		catch(Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

		}
		
	}
	
}
