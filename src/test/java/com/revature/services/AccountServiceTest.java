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
	private Account testAccount2 = new Account(1, "Primary Checking", 10000.00,"A really nice bank account to track my checking account transactions", Instant.now(), null);
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

	@Test
	void testGetAllTransactions() {
		Account testAccount = new Account(0, null, 0.0,null, null, null);
		Mockito.when(accountRepository.getById(Mockito.anyInt())).thenReturn(testAccount);
		Mockito.when(transRepo.findByAccount(Mockito.any(Account.class))).thenReturn(testTransaction);
		List<Transaction> serviceTest = as.getAllTransactions(1);

		
		assertEquals(serviceTest, testTransaction);
	}
	
	@Test
	void testFindByAccountIdReturnsNotNull() {
		accountId = 6;
		Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of((new Account(1, "Primary Checking", 10000.00,"A really nice bank account to track my checking account transactions", Instant.now(), null))));
		assertNotEquals(null, as.findByAccountId(accountId));
	}
	
	@Test
	void testFindByAccountIdReturnsNull() {
		when(accountRepository.findById(accountId)).thenReturn(Optional.of((new Account(1, "Primary Checking", 10000.00,"A really nice bank account to track my checking account transactions", Instant.now(), null))));
		accountId = 1;
		assertEquals(null, as.findByAccountId(accountId));
	}
	
	@Test
	void testInsertAccountReturnsNull() {
		Mockito.when(accountRepository.save(testAccount2)).thenReturn(testAccount2);
		
		Account returnedAccount = as.insertAccount(testAccount2, testUser);
		
		assertEquals(testAccount2, returnedAccount);		
	}
	
	@Test
	void testUpdateAccountReturnsAccount() {
		Mockito.when(accountRepository.saveAndFlush(testAccount2)).thenReturn(testAccount2);
		when(accountRepository.getById(testAccount2.getId())).thenReturn(testAccount2);
		
		Account returnedUpdatedAccount = as.updateAccount(testAccount2, testUser);
		
		assertEquals(testAccount2, returnedUpdatedAccount);
		
	}

}
