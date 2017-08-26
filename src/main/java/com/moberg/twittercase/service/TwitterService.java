package com.moberg.twittercase.service;

import java.util.List;

public interface TwitterService {

	/**
	 * Get most used words in tweets including hashtag
	 * 
	 * @param hashtag, the twitter hashtag
	 * @param nrOfResults, how many top words to return
	 * @return Most used words in tweets with hashtag
	 */
	List<TwitterWord> getTopWordsForHashtag(String hashtag, int nrOfResults);
	
}
