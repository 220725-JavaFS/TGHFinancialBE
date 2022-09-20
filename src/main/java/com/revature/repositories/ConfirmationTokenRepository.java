package com.revature.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.ConfirmationToken;
import com.revature.models.User;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

	Optional<ConfirmationToken> findByToken(String token);
	
	@Transactional
	long deleteByUser(User user);

}
