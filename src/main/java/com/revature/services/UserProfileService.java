package com.revature.services;

import com.revature.models.UserProfile;
import com.revature.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserProfileService {

    private final UserProfileRepository profileRepository;

    public UserProfile saveProfile(UserProfile profile) {
        return profileRepository.save(profile);
    }

    public Optional<UserProfile> findProfileForUser(int userId) {
        return profileRepository.findByUserId(userId);
    }

    public UserProfileService(UserProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

}
