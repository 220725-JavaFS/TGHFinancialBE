package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

class UserServiceTest {
	
	private User testUser = new User(1, "example@example.com", "password");
	private static UserService us;
	private static UserRepository mockRepo = Mockito.mock(UserRepository .class);
	
	@BeforeAll
	public static void createUserService() {
		us = new UserService(mockRepo);
	}

	@Test
	void testFindUserByEmail() {
		Mockito.when(us.findUserByEmail("example@example.com")).thenReturn(new User(1, "example@example.com", "password"));
		User user = us.findUserByEmail("example@example.com");
		assertEquals(testUser, user);
	}

	@Test
	void testFindById() {
		Mockito.when(us.findById(1)).thenReturn(new User(1, "example@example.com", "password"));
		User user = us.findById(1);
		assertEquals(testUser, user);
	}

	@Test
	void testFindByCredentials() {
		Optional<User> testOptional = mockRepo.findByEmailAndPassword("example@example.com", "password");
		Mockito.when(us.findByCredentials("example@example.com", "password")).thenReturn(testOptional);
		Optional<User> user = us.findByCredentials("example@example.com", "password");
		assertEquals(testOptional, user);
	}

	@Test
	void testFindByEmail() {
		Optional<User> testOptional = mockRepo.findByEmail("example@example.com");
		Mockito.when(us.findByEmail("example@example.com")).thenReturn(testOptional);
		Optional<User> user = us.findByEmail("example@example.com");
		assertEquals(testOptional, user);
	}

	@Test
	void testSave() {
		User testSave = mockRepo.save(new User(1, "example@example.com", "password"));
		Mockito.when(us.save(testUser)).thenReturn(testSave);
		User user = us.save(testUser);
		assertEquals(testSave, user);
	}

}
