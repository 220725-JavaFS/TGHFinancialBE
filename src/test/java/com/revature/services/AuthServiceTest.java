package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.models.ConfirmationToken;
import com.revature.models.User;
import com.revature.repositories.ConfirmationTokenRepository;
import com.revature.repositories.UserRepository;

class AuthServiceTest {

	private User testUser = new User(1, "example@example.com", "password");
	private ConfirmationToken testToken = new ConfirmationToken(testUser);
	private static UserService us;
	private static ConfirmationTokenRepository confirmationTokenRepository = Mockito.mock(ConfirmationTokenRepository .class);
	private static UserRepository userRepo = Mockito.mock(UserRepository .class);
	private static AuthService as;
	
	@BeforeAll
	public static void createAuthService() {
		us = new UserService(userRepo);
		as = new AuthService(us, confirmationTokenRepository);
	}

	@Test
	void testFindByCredentials() {
		Optional<User> testOptional = us.findByCredentials("example@example.com", "password");
		Mockito.when(as.findByCredentials("example@example.com", "password")).thenReturn(testOptional);
		Optional<User> user = as.findByCredentials("example@example.com", "password");
		assertEquals(testOptional, user);
	}

	@Test
	void testFindByEmail() {
		Optional<User> testOptional = us.findByEmail("example@example.com");
		Mockito.when(as.findByEmail("example@example.com")).thenReturn(testOptional);
		Optional<User> user = as.findByEmail("example@example.com");
		assertEquals(testOptional, user);
	}

	@Test
	void testFindUserByEmail() {
		Mockito.when(as.findUserByEmail("example@example.com")).thenReturn(new User(1, "example@example.com", "password"));
		User user = as.findUserByEmail("example@example.com");
		assertEquals(testUser, user);
	}

	@Test
	void testFindById() {
		Mockito.when(as.findById(1)).thenReturn(new User(1, "example@example.com", "password"));
		User user = as.findById(1);
		assertEquals(testUser, user);
	}

	@Test
	void testUpdateUser() {
		User testSave = userRepo.save(new User(1, "example@example.com", "password"));
		Mockito.when(as.updateUser(testUser)).thenReturn(testSave);
		User user = as.updateUser(testUser);
		assertEquals(testSave, user);
	}

	@Test
	void testRegister() {
		User testSave = userRepo.save(new User(1, "example@example.com", "password"));
		Mockito.when(as.register(testUser)).thenReturn(testSave);
		User user = as.register(testUser);
		assertEquals(testSave, user);
	}

	@Test
	void testFindByConfirmationToken() {
		Optional<ConfirmationToken> testOptional = confirmationTokenRepository.findByToken("testToken");
		Mockito.when(as.findByConfirmationToken("testToken")).thenReturn(testOptional);
		Optional<ConfirmationToken> token = as.findByConfirmationToken("testToken");
		assertEquals(testOptional, token);
	}

	@Test
	void testStoreToken() {
		ConfirmationToken testSave = confirmationTokenRepository.save(testToken);
		as.storeToken(testSave);
		assertEquals(testSave, null);
	}

}