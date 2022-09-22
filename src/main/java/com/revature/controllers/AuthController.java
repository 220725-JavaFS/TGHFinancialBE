package com.revature.controllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.ConfirmationToken;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.EmailSenderService;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "ec2-35-88-77-72.us-west-2.compute.amazonaws.com:80/"}, allowCredentials = "true")
public class AuthController {

	private static Logger log = LoggerFactory.getLogger(AuthController.class);
	private final AuthService authService;
    private EmailSenderService emailSenderService;

	public AuthController(AuthService authService,  EmailSenderService emailSenderService) {
		this.authService = authService;
		this.emailSenderService = emailSenderService;
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
		//log user
    	log.info("User info given: " + loginRequest);
		Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());
		//log optional
    	log.info("Existing user found: " + optional);
		if (!optional.isPresent()) {
			//log error
			log.error("Error: no such user.");
			return ResponseEntity.badRequest().build();
		}

		session.setAttribute("user", optional.get());
		//log session attribute
		log.info("Session Attribute: " + session.toString());
		
        return ResponseEntity.ok(optional.get());
    }
    
    @PutMapping("/reset-password")
    public ResponseEntity<User> resetPassword(@RequestBody User user) {
    	//log user ID
    	log.info("User ID given: " + user.getId());
    	//find user by email (implicitly finds the correct user ID)
    	User existingUser = authService.findUserByEmail(user.getEmail());
    	//log existingUser ID
    	log.info("Correct User ID: " + existingUser.getId());
    	//if user Id received != correct user ID then throw exception
    	if (user.getId() != existingUser.getId()){
    		//log error
    		log.error("Error: no such user.");
    		return ResponseEntity.notFound().build();
    	}
    	//log previous password 
    	log.info("Previous Password: " + existingUser.getPassword());
    	//log updated password 
    	log.info("Updated Password: " + user.getPassword());
    	//log saved user password
    	log.info("Saved user: " + user);
    	
    	authService.deleteConfirmationTokenByUser(existingUser);
    	
		return ResponseEntity.ok().body(authService.updateUser(user));
    }

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpSession session) {
		session.removeAttribute("user");
		log.info("Session attribute has been removed.");
		return ResponseEntity.ok().build();
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
		User created = new User(0, registerRequest.getEmail(), registerRequest.getPassword());
		//log user
		log.info("New User created: " + created);
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(created));
	}

	@PostMapping("/forgot")
	public ResponseEntity<Void> forgotPassword(@RequestBody User user) {

		Optional<User> optional = authService.findByEmail(user.getEmail());
		
		if (!optional.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
		
		user = optional.get();
		
		if (user == null) {
			return ResponseEntity.badRequest().build();
		}
		
		emailSenderService.sendPasswordResetEmail(user);

		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/confirm-reset")
    public ResponseEntity<ConfirmationToken> validateResetToken(@RequestBody ConfirmationToken confirmationToken) {
		
		Optional<ConfirmationToken> optional = authService.findByConfirmationToken(confirmationToken.getToken());

        if (!optional.isPresent()) {
			return ResponseEntity.badRequest().build();
		}
        
        return ResponseEntity.ok().body(optional.get());
    }
}
