package com.revature.services;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.TransactionType;
import com.revature.models.User;
import com.revature.repositories.AccountRepository;
import com.revature.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;
    

    public Account findByAccountId(int accountId) {
    	Optional<Account> optional = accountRepository.findById(accountId);
    	
    	if(!optional.isPresent()) {
    		return null;
    	}else {
    		return optional.get();
    	}
        
    }

    public List<Transaction> getAllTransactions(int accountId) {
        Account account = accountRepository.getById(accountId);
        return transactionRepository.findByAccount(account);
    }

    public Transaction upsertTransaction(int accountId, Transaction transactionToUpsert) {

            Account account = accountRepository.getById(accountId);

            if(transactionToUpsert.getType() == TransactionType.Expense) {
                account.setBalance(account.getBalance() - transactionToUpsert.getAmount());
            } else if (transactionToUpsert.getType() == TransactionType.Income) {
                account.setBalance(account.getBalance() + transactionToUpsert.getAmount());
            }
            accountRepository.saveAndFlush(account);
            transactionToUpsert.setAccount(account);
            return transactionRepository.save(transactionToUpsert);
    }

    // added - insert new account. needs testing
	public Account insertAccount(Account account, User user) {
		account.setUser(user);
		account.setCreationDate(Instant.now());
		return accountRepository.save(account);
	}

	//added - updates account info. needs testing but it works!
	public Account updateAccount(Account account, User user) {
		account.setUser(user);
		
		Account updatedAccount = accountRepository.getById(account.getId());
        updatedAccount.setDescription(account.getDescription());
        updatedAccount.setName(account.getName());
        updatedAccount.setCreationDate(Instant.now());
        return accountRepository.saveAndFlush(updatedAccount);
		}

}
