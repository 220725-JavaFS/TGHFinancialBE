package com.revature.controllers;

import java.util.Optional;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.ConfirmationToken;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.EmailSenderService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" }, allowCredentials = "true")
public class AuthController {

	private final AuthService authService;
    private EmailSenderService emailSenderService;

	public AuthController(AuthService authService,  EmailSenderService emailSenderService) {
		this.authService = authService;
		this.emailSenderService = emailSenderService;
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
		Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

		if (!optional.isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		session.setAttribute("user", optional.get());

		return ResponseEntity.ok(optional.get());
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpSession session) {
		session.removeAttribute("user");

		return ResponseEntity.ok().build();
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
		User created = new User(0, registerRequest.getEmail(), registerRequest.getPassword());

		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(created));
	}

	@PostMapping("/forgot")
	public ResponseEntity<Void> forgotPassword(@RequestBody User user) {

		System.out.println(user.toString());

		user = authService.findByEmail(user.getEmail()).get();

		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Complete Password Reset!");
		mailMessage.setFrom("TGHFinancial@outlook.com");
		mailMessage.setText("To complete the password reset process, please click here: "
				+ "http://localhost:8082/confirm-reset?token=" + confirmationToken.getConfirmationToken());

		emailSenderService.sendEmail(mailMessage);

		return ResponseEntity.ok().build();
	}
}
