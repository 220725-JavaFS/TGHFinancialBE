package com.revature.services;

import com.revature.models.User;
import com.revature.models.UserProfile;
import com.revature.repositories.UserProfileRepository;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository profileRepository;

    public UserService(UserRepository userRepository, UserProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public User findById(int id) {
        return userRepository.getById(id);
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public UserProfile saveProfile(UserProfile profile) {
        return profileRepository.save(profile);
    }

    public Optional<UserProfile> findProfileForUser(int userId) {
        return profileRepository.findByUserId(userId);
    }
}
