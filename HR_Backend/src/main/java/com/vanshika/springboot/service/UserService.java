package com.vanshika.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanshika.springboot.model.User;
import com.vanshika.springboot.repository.UserRepo;
import java.util.Optional;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	
	public Integer validateUser(String username, String password){
		User user = userRepo.findByUsernameAndPassword(username, password).orElse(null);
		if(user == null)return null;
		return user.getUserId();
	}

	public Optional<User> authenticate(String username, String password) {
		return userRepo.findByUsernameAndPassword(username, password);
	}
	
	public User getUsernameByUserId(int userId) {
		return userRepo.getUsernameByUserId(userId);
	}

}
