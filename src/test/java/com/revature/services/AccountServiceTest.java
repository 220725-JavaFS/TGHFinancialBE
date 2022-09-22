package com.revature.services;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.repositories.AccountRepository;
import com.revature.repositories.TransactionRepository;
import com.revature.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AccountServiceTest {

	private User testUser = new User(1, "example@example.com", "password");
	private Optional<Account> testAccount = Optional.of(new Account(1, "Primary Checking", 10000.00,"A really nice bank account to track my checking account transactions", Instant.now(), null));
	List<Transaction> testTransaction = new ArrayList<Transaction>(0);
	private int accountId;
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private TransactionRepository transRepo;
	@Mock
	private UserService userService;
	@InjectMocks
	private AccountService as;
	
	@BeforeEach
	public void userService() {
		MockitoAnnotations.openMocks(this);
		accountId = 0;
	}
	
//	@Test
//	void testFindByAccountId() {
//		
//		Mockito.when(userService.findById(1)).thenReturn(testUser);
//		Mockito.when(accountRepository.findByUser(Mockito.any(User.class))).thenReturn(testAccount);
//		Account serviceTest = as.findByAccountId(1);
//
//		
//		assertEquals(serviceTest, testAccount);
//	}

	@Test
	void testGetAllTransactions() {
		Account testAccount = new Account(0, null, 0.0,null, null, null);
		Mockito.when(accountRepository.getById(Mockito.anyInt())).thenReturn(testAccount);
		Mockito.when(transRepo.findByAccount(Mockito.any(Account.class))).thenReturn(testTransaction);
		List<Transaction> serviceTest = as.getAllTransactions(1);

		
		assertEquals(serviceTest, testTransaction);
	}
	
	@Test
	void testFindByAccountIdReturnsNull() {
		
		when(accountRepository.findById(accountId)).thenReturn(testAccount);
		accountId = 6;
		assertEquals(null, as.findByAccountId(accountId));
	}
	
	@Test
	void testFindByAccountIdReturnsNotNull() {
		
		when(accountRepository.findById(accountId)).thenReturn(testAccount);
		accountId = 1;
		assertNotEquals(null, as.findByAccountId(accountId));
	}

}
