package com.moberg.twittercase.service;

import java.util.List;

public interface TwitterService {

	List<TwitterWord> getTopWordsForHashtag(String hashtag, int nrOfResults);
	
}
