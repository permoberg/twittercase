package com.moberg.twittercase.service;

import java.io.Serializable;

public class TwitterWord implements Serializable {

	private static final long serialVersionUID = 1L;
	private String word;
	private Long count;
	
	public TwitterWord() {
		// Empty constructor for JSON
	}

	public TwitterWord(String word, Long count) {
		// Empty constructor for JSON
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "TwitterWord [word=" + word + ", count=" + count + "]";
	}
	
}
