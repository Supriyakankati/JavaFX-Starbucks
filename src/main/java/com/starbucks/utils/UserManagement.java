package com.starbucks.utils;

import com.starbucks.model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserManagement {

	private static final String USERS_FILE = "src/main/resources/users.txt";

	// Write a new user to the users.txt file
	public void createUser(User user) throws IOException {
		String userRecord = user.getUsername() + ";" + user.getPassword() + ";" + user.getBalance();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
			writer.write(userRecord);
			writer.newLine();
		}
	}

	// Read users from users.txt and return them as a list
	public List<User> readUsers() throws IOException {
		if (!Files.exists(Paths.get(USERS_FILE))) {
			return new ArrayList<>();
		}

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(USERS_FILE))) {
			return reader.lines().map(this::parseUser).collect(Collectors.toList());
		}
	}

	// Authenticate a user by username and password
	public User authenticateUser(String username, String password) throws IOException {
		List<User> users = readUsers();
		return users.stream().filter(user -> user.getUsername().equals(username) 
				&& user.getPassword().equals(password))
				.findFirst().orElse(null);
	}

	// Helper method to parse a line from the users.txt file into a User object
	private User parseUser(String line) {
		String[] parts = line.split(";");
		if (parts.length != 3) {
			throw new IllegalArgumentException("Invalid user record: " + line);
		}
		return new User(parts[0], parts[1], Double.parseDouble(parts[2]));
	}
}
