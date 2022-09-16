package com.revature.models;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String firstName;
	String lastName;
	String streetAddress;
	String city;
	String state;
	char zipcode;
	char telephone;
	
	@OneToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    User user;
	
	public UserProfile(String firstName, String lastName, String streetAddress, String city,
			String state, char zipcode, char telephone, User user) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state= state;
		this.zipcode = zipcode;
		this.telephone = telephone;
		}

}
