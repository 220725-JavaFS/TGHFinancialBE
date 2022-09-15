package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
	
	public User(int id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.accounts = new LinkedList<Account>();
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts;
    
    
    public List<Account> addAccount(Account accountToAdd) {
    	accounts.add(accountToAdd);
    	return accounts;
    }
    
}
