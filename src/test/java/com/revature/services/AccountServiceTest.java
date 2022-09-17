package com.revature.services;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.repositories.AccountRepository;
import com.revature.repositories.TransactionRepository;
import com.revature.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class AccountServiceTest {

	private Instant instant = Instant.now();
	private User testUser = new User(1, "example@example.com", "password");
	private Account testAccount = new Account("Primary Checking	", "A really nice bank account to track my checking account transactions", 
			instant, testUser);
	@InjectMocks
	private AccountService accountService = Mockito.mock(AccountService.class);
	private static AccountRepository mockAcctRepo = Mockito.mock(AccountRepository.class);
	private static TransactionRepository mockTransRepo = Mockito.mock(TransactionRepository.class);
	private static UserRepository mockRepo = Mockito.mock(UserRepository.class);
	@Mock
	private static UserService userService = Mockito.mock(UserService.class);
	
	@BeforeAll
	public static void userService() {
		userService.findById(1);
	}
	
	@Test
	void testFindByUserId() {
		Mockito.when(userService.findById(1)).thenReturn(testUser);
		Optional<Account> testOptional = mockAcctRepo.findByUser(testUser);
		assertEquals(testOptional, testAccount);
	}

	@Test
	void testUpsertAccount() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllTransactions() {
		fail("Not yet implemented");
	}

	@Test
	void testUpsertTransaction() {
		fail("Not yet implemented");
	}


}
