package com.vanshika.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vanshika.springboot.model.User;
import com.vanshika.springboot.repository.UserRepo;
import com.vanshika.springboot.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping("/validate")
	Integer validateUser(@RequestParam String username,@RequestParam String password){
		Integer userId = userService.validateUser(username, password);
		return userId;
	}
}
