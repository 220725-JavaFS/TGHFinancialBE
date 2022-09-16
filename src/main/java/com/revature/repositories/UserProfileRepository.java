package com.revature.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.User;
import com.revature.models.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer>{
	
	Optional<UserProfile> findByUser(User user);
}