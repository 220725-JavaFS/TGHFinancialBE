package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}
	
	  public List<Account> getAllAccounts(int userId) {
	    	return this.findById(userId).getAccounts();
	    }
	 

	public User findById(int id) {
		return userRepository.getById(id);
	}

	public Optional<User> findByCredentials(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

}

