package com.moberg.twittercase.util;

import java.util.regex.Pattern;

import com.moberg.twittercase.exceptions.InvalidInputException;

public class InputValidator {

	// First letter non-numeric
	// Assuming max 30 characters
	// Assuming swedish letters
	// Letters, numbers and underscore is valid
	private static final Pattern HASHTAG_PATTERN = Pattern.compile("^[a-zåäöA-ZÅÄÖ_]{1}[a-zåäöA-ZÅÄÖ_0-9]{0,29}");
	
	private InputValidator() {
		// Hidden constructor
	}
	
	public static void validate(Object input, InputType type) {
		
		if(type == null) {
			throw new InternalError("Input can not be null");
		}
		
		switch(type) {
		case HASHTAG: 
			validateHashtag(input);
			break;
		case POSITIVE_NUMBER:
			validatePositiveNumber(input);
			break;
		default:
			throw new InternalError("validation of type " + type + "not implemented yet");
		}
		
	}
	
	private static void validatePositiveNumber(Object input) {
		if(!(input instanceof Integer && ((Integer)input) > 0)) {
			throw new InvalidInputException(input + "is not a valid input");
		}
	}

	private static void validateHashtag(Object input) {
		if(!(input instanceof String && HASHTAG_PATTERN.matcher((String)input).matches())) {
			throw new InvalidInputException(input + "is not a valid input");
		}
	}
	
	public enum InputType {
		HASHTAG, POSITIVE_NUMBER
	}
	
}
