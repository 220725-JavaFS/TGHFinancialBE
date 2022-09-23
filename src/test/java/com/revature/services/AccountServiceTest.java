package com.revature.services;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.TransactionType;
import com.revature.models.User;
import com.revature.repositories.AccountRepository;
import com.revature.repositories.TransactionRepository;
import com.revature.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

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
	private Optional<Account> testAccount2 = Optional.of(new Account(2, "Primary Checking", 10000.00,"A really nice bank account to track my checking account transactions", Instant.now(), null));
	private Transaction testSendTransaction = new Transaction(1, 100.00, "checking", TransactionType.Expense, null);
	List<Transaction> testTransaction = new ArrayList<Transaction>(0);
	
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
	}
	
	@Test
	void testFindByUserId() {
		
		Mockito.when(userService.findById(1)).thenReturn(testUser);
		Mockito.when(accountRepository.findByUser(Mockito.any(User.class))).thenReturn(testAccount);
		Optional<Account> serviceTest = as.findByUserId(1);

		
		assertEquals(serviceTest, testAccount);
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
	void testSendMoneyTransaction() {
		
		Account testAccount = new Account (1, null, 1000.00, null, null, null);
		Account testReceiveAccount = new Account (2, null, 1000.00, null, null, null);
		Transaction testSendTransaction = new Transaction(1, 1500.00, "checking", TransactionType.Expense, null);
		
		int accountId = 1;
		int receiveId = 2;
		
		Mockito.when(accountRepository.getById(accountId)).thenReturn(testAccount);
		Mockito.when(accountRepository.getById(receiveId)).thenReturn(testReceiveAccount);
		
		Transaction serviceTest = as.sendMoneyTransaction(accountId, testSendTransaction, receiveId);
		
		//test if null
		assertEquals(serviceTest, null);
		
		//THEN test if not null
		testSendTransaction = new Transaction (1, 100.00, "checking", TransactionType.Expense, null);
		
		serviceTest = as.sendMoneyTransaction(accountId, testSendTransaction, receiveId);
		
		assertEquals(serviceTest, testSendTransaction);
		
		
}
}
