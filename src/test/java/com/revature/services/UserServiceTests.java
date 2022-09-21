package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.models.User;
import com.revature.models.UserProfile;
import com.revature.repositories.UserProfileRepository;
import com.revature.repositories.UserRepository;

public class UserServiceTests {

	private UserRepository mockUserRepo = Mockito.mock(UserRepository.class);
	private UserProfileRepository mockProfileRepo = Mockito.mock(UserProfileRepository.class);
	private UserService userService = new UserService(mockUserRepo, mockProfileRepo);
	private User user = new User (1, "email", "passord");
	private Optional<UserProfile> testProfile = Optional.of(new UserProfile(1, "firstName", "lastName", "address", "city", "state", "zipCode", "phone", user));
	
	
	@Test
	public void testFindProfile() {
		Mockito.when(mockProfileRepo.findByUserId(1))
			.thenReturn(testProfile);
		Optional<UserProfile> userProfile = userService.findProfileForUser(1);
		assertEquals(testProfile, userProfile);
	}
	
	
	
	
	
	
	
	
}
