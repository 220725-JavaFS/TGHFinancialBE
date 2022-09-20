package com.revature.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.models.ConfirmationToken;
import com.revature.models.User;
import com.revature.repositories.ConfirmationTokenRepository;

@Service
public class AuthService {

    private final UserService userService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public AuthService(UserService userService, ConfirmationTokenRepository confirmationTokenRepository) {
        this.userService = userService;
		this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userService.findByCredentials(email, password);
    }
    
    public Optional<User> findByEmail(String email) {
        return userService.findByEmail(email);
    }
    
    public User findUserByEmail(String email) {
        return userService.findUserByEmail(email);
    }
    
    public User findById(int id) {
        return userService.findById(id);
    }
    
    public User updateUser(User user) {
    	return userService.save(user);
    }

    public User register(User user) {
        return userService.save(user);
    }
    
    public Optional<ConfirmationToken> findByConfirmationToken(String token){
    	return confirmationTokenRepository.findByToken(token);
    }
    
    public long deleteConfirmationTokenByUser(User user){
    	return confirmationTokenRepository.deleteByUser(user);
    }
    
    public ConfirmationToken storeToken(ConfirmationToken confirmationToken){
    	confirmationToken = confirmationTokenRepository.save(confirmationToken);
    	return confirmationToken;
    }
}
