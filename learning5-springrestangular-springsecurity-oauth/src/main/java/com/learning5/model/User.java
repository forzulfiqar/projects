package com.learning5.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 
 * @author zulfiqar
 * Any type of user in the applicaiton
 */

@Entity
@Table(name = "userofapp")
//@ManagedBean(name = "user")
public class User {
	
	public static final String DEFAULT_SORT_COLUMN = "firstName";

	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "User_Seq_Gen", sequenceName = "user_sequence")
	@GeneratedValue(generator = "User_Seq_Gen")
	private long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "country_id")
	private long countryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", insertable = false, updatable = false, nullable = false)
	private Country country;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public String toString() {
		StringBuilder sB = new StringBuilder("");
		
		return "User= Id: " + this.getId() + ", userName: " + this.getUserName();
	}
	
}
