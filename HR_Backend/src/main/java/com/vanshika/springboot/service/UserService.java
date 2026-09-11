package com.vanshika.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanshika.springboot.model.User;
import com.vanshika.springboot.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	
	public Integer validateUser(String username, String password){
		User user = userRepo.getUserByUsernameAndPassword(username, password);
		if(user == null)return null;
		return user.getUserId();
	}
	
	public User getUsernameByUserId(int userId) {
		return userRepo.getUsernameByUserId(userId);
	}
	
}
