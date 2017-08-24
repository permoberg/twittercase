package com.moberg.twittercase.service;

import java.io.Serializable;

public class TwitterWord implements Serializable {

	private static final long serialVersionUID = 1L;
	private String word;
	private int count;
	
	public TwitterWord() {
		// Empty constructor for JSON
	}

	public TwitterWord(String word, int count) {
		this.word = word;
		this.count = count;
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "TwitterWord [word=" + word + ", count=" + count + "]";
	}
	
}
