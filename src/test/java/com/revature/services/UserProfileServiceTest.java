package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.models.User;
import com.revature.models.UserProfile;
import com.revature.repositories.UserProfileRepository;
import com.revature.repositories.UserRepository;

public class UserProfileServiceTest {

	private UserProfileRepository mockProfileRepo = Mockito.mock(UserProfileRepository.class);
	private UserProfileService profileService = new UserProfileService(mockProfileRepo);
	private User user = new User (1, "email", "passord");
	private Optional<UserProfile> testProfile = Optional.of(new UserProfile(1, "firstName", "lastName", "address", "city", "state", "zipCode", "phone", user));
	private UserProfile profile = new UserProfile(1, "firstName", "lastName", "address", "city", "state", "zipCode", "phone", user);
	
	@Test
	public void testFindProfile() {
		Mockito.when(mockProfileRepo.findByUserId(1))
			.thenReturn(testProfile);
		Optional<UserProfile> userProfile = profileService.findProfileForUser(1);
		assertEquals(testProfile, userProfile);
	}
	
	@Test
	public void testSaveProfile() {
		UserProfile testSaveProfile = new UserProfile(1, "firstName", "lastName", "address", "city", "state", "zipCode", "phone", user);
		
		Mockito.when(mockProfileRepo.save(profile))
			.thenReturn(testSaveProfile);
		UserProfile userProfile = profileService.saveProfile(profile);
		assertEquals(testSaveProfile, userProfile);
	}
}
