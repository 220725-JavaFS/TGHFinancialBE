package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.notNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.revature.models.ConfirmationToken;
import com.revature.models.User;

public class EmailSenderServiceTest {
	
	private User testUser = new User(1, "example@example.com", "password");
	@Mock
	private static AuthService as;
	@InjectMocks
	private static EmailSenderService ess;
	
	@Spy
	private EmailSenderService essSpy;
	@Mock
	private static JavaMailSender jms;

	static SimpleMailMessage testmail = new SimpleMailMessage();
	
	@BeforeAll
	public static void createUserService() {
		testmail.setTo("test");
		testmail.setSubject("test");
		testmail.setFrom("test");
		testmail.setText("test");
	}
	
	@BeforeEach
	public void userService() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testSendPasswordResetEmail() {
		Mockito.when(as.storeToken((ConfirmationToken)notNull())).thenReturn(null);
		Mockito.doReturn(testmail).when(essSpy).buildEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		Mockito.doReturn(true).when(essSpy).sendEmail(testmail);
		boolean test = ess.sendPasswordResetEmail(testUser);
		assertTrue(test);
	}
	
	@Test
	void testSendEmail() {
		boolean testSend = ess.sendEmail(testmail);
		assertTrue(testSend);
	}
	
	@Test 
	void testBuildEmail() {
		String test = "test";
		SimpleMailMessage mail = ess.buildEmail(test, test, test, test);
		assertEquals(testmail, mail);
	}
	
}
