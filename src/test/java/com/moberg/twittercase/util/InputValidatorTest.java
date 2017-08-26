package com.moberg.twittercase.util;

import org.junit.Test;

import com.moberg.twittercase.exceptions.InvalidInputException;
import com.moberg.twittercase.util.InputValidator.InputType;

public class InputValidatorTest {

	@Test(expected=InternalError.class)
	public void nullInputType() {
		InputValidator.validate("valid", null);
	}
	
	@Test(expected=InvalidInputException.class)
	public void nullHashtag() {
		InputValidator.validate(null, InputType.HASHTAG);
	}
	
	@Test(expected=InvalidInputException.class)
	public void emptyHashtag() {
		InputValidator.validate("", InputType.HASHTAG);
	}
	
	@Test(expected=InvalidInputException.class)
	public void hashtagWithSpace() {
		InputValidator.validate("test test", InputType.HASHTAG);
	}
	
	@Test(expected=InvalidInputException.class)
	public void hashtagStartsWithNumber() {
		InputValidator.validate("1test", InputType.HASHTAG);
	}
	
	@Test(expected=InvalidInputException.class)
	public void hashtagToLong() {
		InputValidator.validate("A012345678901234567890123456789", InputType.HASHTAG);
	}
	
	@Test
	public void validHashtags() {
		InputValidator.validate("abcdefghijklmnopqrstuvwxyzåäö", InputType.HASHTAG);
		InputValidator.validate("ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ", InputType.HASHTAG);
		InputValidator.validate("A1234567890", InputType.HASHTAG);
		InputValidator.validate("best_test_ever", InputType.HASHTAG);
	}
	
	@Test(expected=InvalidInputException.class)
	public void nullPositiveNumber() {
		InputValidator.validate(null, InputType.POSITIVE_NUMBER);
	}
	
	@Test(expected=InvalidInputException.class)
	public void NonNumberPositiveNumber() {
		InputValidator.validate("nonNumber", InputType.POSITIVE_NUMBER);
	}
	
	@Test(expected=InvalidInputException.class)
	public void NonPositiveNumber() {
		InputValidator.validate("0", InputType.POSITIVE_NUMBER);
	}
	
	@Test
	public void validPositiveNumber() {
		InputValidator.validate(Integer.valueOf(1), InputType.POSITIVE_NUMBER);
		InputValidator.validate(Integer.valueOf(15), InputType.POSITIVE_NUMBER);
		InputValidator.validate(Integer.valueOf(100), InputType.POSITIVE_NUMBER);
	}
	
}
