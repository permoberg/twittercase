package com.moberg.twittercase.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.moberg.twittercase.exceptions.InvalidInputException;

public class InputValidator {

	// First letter non-numeric
	// Assuming max 30 characters
	// Letters, numbers and underscore is valid
	private static final Pattern HASHTAG_PATTERN = Pattern.compile("^[a-zåäöA-ZÅÄÖ_]{1}[a-zåäöA-ZÅÄÖ_0-9]{0,29}");
	
	private InputValidator() {
		// Hide constructor
	}
	
	public static void validate(String input, InputType type) {
		
		if(type == null) {
			throw new InternalError("Input type can not be null");
		}
		
		if(StringUtils.isEmpty(input)) {
			throw new InvalidInputException(input + "is not a valid input");
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
	
	private static void validatePositiveNumber(String input) {
		if(!StringUtils.isNumeric(input) || Integer.valueOf(input).intValue() <= 0) {
			throw new InvalidInputException(input + "is not a valid input");
		}
	}

	private static void validateHashtag(String input) {
		if(!HASHTAG_PATTERN.matcher(input).matches()) {
			throw new InvalidInputException(input + "is not a valid input");
		}
	}
	
	public enum InputType {
		HASHTAG, POSITIVE_NUMBER
	}
	
}
