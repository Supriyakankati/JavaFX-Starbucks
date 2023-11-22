package com.starbucks.utils;

import java.util.Objects;

public class Validation {

	// Validate that a string is neither null nor empty
	public static String validateString(String value, String fieldName) {
		Objects.requireNonNull(value, fieldName + " cannot be null");
		if (value.trim().isEmpty()) {
			throw new IllegalArgumentException(fieldName + " cannot be empty");
		}
		return value;
	}
}
