package com.revature.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.revature.controllers.AuthController;
import com.revature.models.ConfirmationToken;
import com.revature.models.User;

@Service
public class EmailSenderService {
	
	private String from = "TGHFinancial@outlook.com";
	
	private static Logger log = LoggerFactory.getLogger(EmailSenderService.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private AuthService authService; 

	@Async
	public boolean sendEmail(SimpleMailMessage email) {
		boolean sentMail = true;
		try{
			javaMailSender.send(email);
		}catch(MailException e) {
			sentMail = false;
			log.error(e.getLocalizedMessage(), e);
		}
		return sentMail;
	}

	public SimpleMailMessage buildEmail(String to, String subject, String from, String text) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setFrom(from);
		mailMessage.setText(text);
		return mailMessage;
	}
	
	public boolean sendPasswordResetEmail(User user) {

		String passwordResetURL = "http://localhost:4200/confirm-reset";

		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		authService.storeToken(confirmationToken);

		String to = user.getEmail();
		String subject = "Complete Password Reset!";
		String from = this.from;
		String text = "To complete the password reset process, please click here: " + passwordResetURL + "?token="
				+ confirmationToken.getToken();
		
		SimpleMailMessage mailMessage = buildEmail(to, subject, from, text);
		
		return sendEmail(mailMessage);
	}
	
	
}
