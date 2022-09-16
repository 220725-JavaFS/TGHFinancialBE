package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.UserProfile;
import com.revature.models.User;
import com.revature.repositories.UserProfileRepository;

@Service
public class UserProfileService {
	
	@Autowired
    private UserProfileRepository userProfileRepository;
	
	@Autowired
    private UserService userService;
	
	public List<UserProfile> getAllUserProfiles(){
		return userProfileRepository.findAll();
	}
	
    public Optional<UserProfile> findByUserId(int id) {
        User user = userService.findById(id);
        return userProfileRepository.findByUser(user);
    }
    
    public UserProfile addUserProfile(UserProfile profileToAdd, String userId) {

        int id = Integer.parseInt(userId);
        User user = userService.findById(id);

        if(userProfileRepository.existsById(profileToAdd.getId())) {
            UserProfile userProfile = userProfileRepository.getById(profileToAdd.getId());
            userProfile.setFirstName(profileToAdd.getFirstName());
            userProfile.setLastName(profileToAdd.getLastName());
            userProfile.setStreetAddress(profileToAdd.getStreetAddress());
            userProfile.setCity(profileToAdd.getCity());
            userProfile.setState(profileToAdd.getState());
            userProfile.setZipcode(profileToAdd.getZipcode());
            userProfile.setTelephone(profileToAdd.getTelephone());
            return userProfileRepository.saveAndFlush(userProfile);
        } else {
            profileToAdd.setUser(user);
            return userProfileRepository.save(profileToAdd);
        }
    }
    
    
    
}
