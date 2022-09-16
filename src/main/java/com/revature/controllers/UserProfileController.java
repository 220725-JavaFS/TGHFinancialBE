package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.UserProfile;
import com.revature.services.UserProfileService;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class UserProfileController {

	@Autowired
    private UserProfileService userProfileService;
	
	@Authorized
	@GetMapping("/all")
	public ResponseEntity<List<UserProfile>> getAllUserProfiles() {
		List<UserProfile> userProfiles = userProfileService.getAllUserProfiles();
		return ResponseEntity.status(200).body(userProfiles);
	}
	
    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfilebyId(@PathVariable("id") int profileId) {
        Optional<UserProfile> optional = userProfileService.findByUserId(profileId);

        if(!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }
	
    @Authorized
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfile userProfile, @RequestHeader("Current-User") String userId) {
        return ResponseEntity.ok(userProfileService.addUserProfile(userProfile, userId));
    }
	
	
	
	
	
	
}
