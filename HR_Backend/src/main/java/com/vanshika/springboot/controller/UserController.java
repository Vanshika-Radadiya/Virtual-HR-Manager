package com.vanshika.springboot.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vanshika.springboot.model.LoginRequest;
import com.vanshika.springboot.model.LoginResponse;
import com.vanshika.springboot.model.User;
import com.vanshika.springboot.service.UserService;

@RestController
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/validate")
	Integer validateUser(@RequestParam String username, @RequestParam String password) {
		Integer userId = userService.validateUser(username, password);
		return userId;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession session) {
		if (request.getUsername() == null || request.getPassword() == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		User user = userService.authenticate(request.getUsername(), request.getPassword()).orElse(null);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		session.setAttribute("userId", user.getUserId());
		session.setAttribute("username", user.getUsername());
		String role = user.getRole() == null ? "candidate" : user.getRole();
		session.setAttribute("role", role);
		return ResponseEntity.ok(new LoginResponse(user.getUserId(), user.getUsername(), role));
	}

	@GetMapping("/session")
	public ResponseEntity<LoginResponse> getSession(HttpSession session) {
		Object userId = session.getAttribute("userId");
		Object username = session.getAttribute("username");
		Object role = session.getAttribute("role");
		if (!(userId instanceof Integer id) || !(username instanceof String name)
				|| !(role instanceof String userRole)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(new LoginResponse(id, name, userRole));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.noContent().build();
	}
}
