package com.starbucks.model;

import com.starbucks.utils.Validation;

public class User {
	private String username;
	private String password;
	
	// User's account balance
	private double balance;

	// Constructor
	public User(String username, String password, double balance) {
		this.username = Validation.validateString(username, "Username");
		this.password = Validation.validateString(password, "Password");
		this.balance = balance;
	}

	// Get the username
	public String getUsername() {
		return username;
	}

	// Set the username
	public void setUsername(String username) {
		this.username = Validation.validateString(username, "Username");
	}

	// Get the password
	public String getPassword() {
		return password;
	}

	// Set the password
	public void setPassword(String password) {
		this.password = Validation.validateString(password, "Password");
	}

	// Get the user's balance
	public double getBalance() {
		return balance;
	}

	// Set the user's balance
	public void setBalance(double balance) {
		this.balance = balance;
	}

	// Deposit funds into the user's account
	public void deposit(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Deposit amount cannot be negative.");
		}
		this.balance += amount;
	}

	// Withdraw funds from the user's account
	public boolean withdraw(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Withdrawal amount cannot be negative.");
		}
		// Withdrawal exceeds the available balance
		if (amount > this.balance) {
			return false; 
		}
		this.balance -= amount;
		return true;
	}

	// Returns a string representation of the user
	@Override
	public String toString() {
		return "User{" + "username='" + username + '\'' + ", balance=" + balance + '}';
	}
}
