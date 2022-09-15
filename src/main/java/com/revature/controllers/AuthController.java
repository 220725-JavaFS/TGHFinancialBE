package com.revature.controllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

		String passwordResetURL = "http://localhost:4200/confirm-reset";

		user = authService.findByEmail(user.getEmail()).get();
		
		if (user == null) {
			return ResponseEntity.badRequest().build();
		}

		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		authService.storeToken(confirmationToken);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Complete Password Reset!");
		mailMessage.setFrom("TGHFinancial@outlook.com");
		mailMessage.setText("To complete the password reset process, please click here: "
				+ passwordResetURL +"?token=" + confirmationToken.getToken());

		emailSenderService.sendEmail(mailMessage);

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
