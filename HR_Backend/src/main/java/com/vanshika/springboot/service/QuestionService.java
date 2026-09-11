package com.vanshika.springboot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.vanshika.springboot.model.AnswerResponse;
import com.vanshika.springboot.model.CompId;
import com.vanshika.springboot.model.EmailDetails;
import com.vanshika.springboot.model.Interview;
import com.vanshika.springboot.model.Question;
import com.vanshika.springboot.model.QuestionResponse;
import com.vanshika.springboot.model.Record;
import com.vanshika.springboot.model.Result;
import com.vanshika.springboot.model.User;
import com.vanshika.springboot.repository.InterviewRepo;
import com.vanshika.springboot.repository.RecordRepo;
import com.vanshika.springboot.repository.ResultRepo;
import com.vanshika.springboot.repository.UserRepo;

@Service
public class QuestionService {
	
	@Autowired
	RecordRepo recordRepo;
	@Autowired
	ResultRepo resultRepo;
	@Autowired
	EmailService emailService;
	@Autowired
	UserService userService;
	@Autowired
	InterviewService interviewService;
	
	
	private int calculateScore(Question q, String ans) {
		Map<String, Integer>mp = q.getKeywords();
		int count = 0;
		int total = 0;
		for(Entry<String,Integer> word: mp.entrySet()) {
			String key = word.getKey();
			int weight = word.getValue();
			total += weight;
			
			if( ans.toLowerCase().contains(key.toLowerCase())) {
				count += weight;
			}
		}
		return count*100/total;
	}
	
	
	public QuestionResponse generateNewQuestion(AnswerResponse ansResp)throws Exception {
		String newCategory = ansResp.getCategory();
		int newLevel = ansResp.getLevel();
		String key1 = ansResp.getKey();
		Question q = null;
		
//calculating individual question score
		
		if(newCategory.equals("oops")) {
			q = QueData.oops.get(newLevel).get(key1);
		}
		else if(newCategory.equals("os")) {
			q = QueData.os.get(newLevel).get(key1);
		}
		else if(newCategory.equals("dbms")) {
			q = QueData.dbms.get(newLevel).get(key1);
		}
		int score = calculateScore(q, ansResp.getAnswer() );
		Record rd = new Record(ansResp.getUserId(), ansResp.getInterviewId(),q.getQue(), ansResp.getAnswer(), score);		
		recordRepo.save(rd);
		
// calculating category score 
		
		Result rs = resultRepo.getByCompId(new CompId(ansResp.getUserId(), ansResp.getInterviewId()));
		if(rs == null) {
			rs = new Result(new CompId(ansResp.getUserId(), ansResp.getInterviewId()), 0, 0, 0);
		}
		if(newCategory.equals("oops")) {
			rs.setOops(rs.getOops() + (score * 33/100));
		}
		else if(newCategory.equals("os")) {
			rs.setOs(rs.getOs() + (score * 33/100));
		}
		else if(newCategory.equals("dbms")) {
			rs.setDbms(rs.getDbms() + (score * 33/100));
		}		
		resultRepo.save(rs);
		

// generating next Question
		
		if(score < 50 || newLevel == 2) {
			int curr = QueData.categories.get(newCategory);
			newCategory = QueData.revCategories.get(curr+1);
			newLevel = 0;
		}
		else {
			newLevel++;
		}
		
		
		if("oops".equals(newCategory)) {
			List<String> keys = QueData.oops.get(newLevel).keySet().stream().toList();
			int random = getRandomInteger(3);
			String key = keys.get(random);
			Question ques = QueData.oops.get(newLevel).get(key);
			System.out.println(key + "  " +random);

			return new QuestionResponse(ques.getQue(), newCategory, newLevel, key );
		}
		else if("os".equals(newCategory)) {
			List<String> keys = QueData.os.get(newLevel).keySet().stream().toList();
			int random = getRandomInteger(3);
			String key = keys.get(random);
			Question ques = QueData.os.get(newLevel).get(key);
			return new QuestionResponse(ques.getQue(), newCategory, newLevel, key );
		}
		else if("dbms".equals(newCategory)) {
			List<String> keys = QueData.dbms.get(newLevel).keySet().stream().toList();
			int random = getRandomInteger(3);
			String key = keys.get(random);
			Question ques = QueData.dbms.get(newLevel).get(key);
			return new QuestionResponse(ques.getQue(), newCategory, newLevel, key );
		}
		else {
//Email Sending via SMTP
			
			User user = userService.getUsernameByUserId(ansResp.getUserId());
			Interview inter = interviewService.getRoleByInterviewId(ansResp.getInterviewId());
			
			String username = user.getUsername();
			String role = inter.getRole();
			
			String subject = "Thank you for completing your virtual interview - "+role+" " ;
			String receiver = "honeyradadiya04@gmail.com";
			String body = "Hi "+ username +", \nThank you for completing your virtual interview as our Screening process. "
					+ "We have successfully received your recorded responses and submission data. Our team will review the "
					+ "interview and reach out to you with the next steps within 3-5 business days.\n"
					+ "Thank you" ;
			EmailDetails details = new EmailDetails(receiver, subject, body);
			
			emailService.sendSimpleMail(details);
			throw new IllegalArgumentException("Test Completedd!!");
		}
		
		
	}
	
	private int getRandomInteger(int upper) {
		return (int)((Math.random()*100) % upper);
	}
}

