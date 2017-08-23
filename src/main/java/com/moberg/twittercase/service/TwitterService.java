package com.moberg.twittercase.service;

import java.util.List;

import twitter4j.Query;
import twitter4j.Status;

public interface TwitterService {

	List<Status> sendQuery(Query query);
	
}
