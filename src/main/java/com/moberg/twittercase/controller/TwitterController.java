package com.moberg.twittercase.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moberg.twittercase.service.TwitterService;
import com.moberg.twittercase.service.TwitterWord;

import twitter4j.Query;
import twitter4j.Status;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

	@Autowired
	private TwitterService twitterService;
	
	@RequestMapping(value="/{hashtag}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<TwitterWord> twitter(@PathVariable("hashtag") String hashtag) {
		
		// Input validation
		
		// Send request
		List<Status> tweets = twitterService.sendQuery(new Query(hashtag));
		
		// Generate result
		return collectResult(tweets, 100);
		
	}
	
	@RequestMapping(value="/{hashtag}/{nrOfResults}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<TwitterWord> twitter(@PathVariable("hashtag") String hashtag, @PathVariable("nrOfResults") String nrOfResultsStr) {
		
		// Input validation
		int nrOfResults = Integer.valueOf(nrOfResultsStr);
		
		// Send request
		List<Status> tweets = twitterService.sendQuery(new Query("#svt"));
		
		// Generate result
		return collectResult(tweets, nrOfResults);
		
	}
	
	private List<TwitterWord> collectResult(List<Status> tweets, int nrOfResults) {
		List<String> tweetWords = new ArrayList<>();
		List<TwitterWord> twitterWords = new ArrayList<>();
		
		// Get List of all words
		tweets.forEach(tweet -> {
    	   if(tweet.getText() != null) {
    		   tweetWords.addAll(Arrays.asList(tweet.getText().toLowerCase().split(" ")));
    		   System.out.println(tweet.getText());
    		   System.out.println();
    	   }
		});
       
       // Get map of word and count
       Map<String, Long> tweetWordMap = 
       		tweetWords.stream().collect(
       				Collectors.groupingBy(
       						Function.identity(), Collectors.counting()));
       System.out.println("**************************************");
       System.out.println(tweetWordMap);
       
       // Sort, limit and add to resulting list
       tweetWordMap.entrySet().stream()
       		.sorted(Map.Entry.<String, Long>comparingByValue().reversed()) 
       		.limit(nrOfResults)
       		.forEach(item -> twitterWords.add(new TwitterWord(item.getKey(), item.getValue())));
       
       return twitterWords;
	}


	
	
	
}


