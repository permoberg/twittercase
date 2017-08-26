package com.moberg.twittercase.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twitter4j.Query;
import twitter4j.Status;

@Service
public class TwitterServiceImpl implements TwitterService {

	@Autowired
	private Twitter4JService twitter4jService;
	
	@Override
	public List<TwitterWord> getTopWordsForHashtag(String hashtag, int nrOfResults) {
		List<Status> tweets = twitter4jService.sendQuery(new Query(hashtag));
		return determineTwitterWordCount(tweets, nrOfResults);
	}

	private List<TwitterWord> determineTwitterWordCount(List<Status> tweets, int nrOfResults) {
		
		// Get List of all words
		List<String> tweetWordList = tweets.stream()
				.map(tweet -> tweet.getText() != null ? Arrays.asList(tweet.getText().toLowerCase().split(" ")) : new ArrayList<String>())
				.flatMap(l -> l.stream())
				.collect(Collectors.toList());
       
       // Get map of word and count
       Map<String, Long> tweetWordMap = 
    		   tweetWordList.stream().collect(
       				Collectors.groupingBy(
       						Function.identity(), Collectors.counting()));
       
       // Sort, limit and add to resulting list
       return tweetWordMap.entrySet().stream()
    		   .sorted((a,b) -> b.getValue().compareTo(a.getValue())) 
    		   .limit(nrOfResults)
    		   .map(entry -> new TwitterWord(entry.getKey(), entry.getValue().intValue()))
    		   .collect(Collectors.toList());

	}

}
