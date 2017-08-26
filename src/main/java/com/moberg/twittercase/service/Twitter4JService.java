package com.moberg.twittercase.service;

import java.util.List;

import twitter4j.Query;
import twitter4j.Status;

public interface Twitter4JService {

	/**
	 * Sends a search query to twitter and returns matching tweets
	 * 
	 * @param query, the twitter query
	 * @return tweets matching query criterias
	 */
	List<Status> sendQuery(Query query);
	
}
