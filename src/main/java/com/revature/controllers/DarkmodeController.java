package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("darkmode")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" }, allowCredentials = "true")
public class DarkmodeController {
	@Autowired
	private UserService userService;
	
	@PutMapping
	public ResponseEntity<User> updateDarkmode(@RequestBody User user) {
		User existingUser = userService.findById(user.getId());
		System.out.println("test");
		System.out.print(user);
		System.out.print(existingUser);
		existingUser.setDarkmode(user.isDarkmode());
		return ResponseEntity.ok(userService.save(existingUser));
	}
}
