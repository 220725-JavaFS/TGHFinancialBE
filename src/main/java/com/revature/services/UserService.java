package com.revature.services;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;

=======
import com.revature.models.User;
import com.revature.models.UserProfile;
import com.revature.repositories.UserProfileRepository;
import com.revature.repositories.UserRepository;
>>>>>>> origin/main
import org.springframework.stereotype.Service;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

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
	public User save(User user) {
		return userRepository.save(user);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

<<<<<<< HEAD
	public User save(User user) {
		return userRepository.save(user);
	}

=======
>>>>>>> origin/main
}


