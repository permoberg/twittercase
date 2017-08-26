package com.moberg.twittercase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moberg.twittercase.service.TwitterService;
import com.moberg.twittercase.service.TwitterWord;
import com.moberg.twittercase.util.InputValidator;
import com.moberg.twittercase.util.InputValidator.InputType;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

	@Autowired
	private TwitterService twitterService;
	
	@RequestMapping(value="/{hashtag}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<TwitterWord> twitter(@PathVariable("hashtag") String hashtag) {
		
		// Input validation
		InputValidator.validate(hashtag, InputType.HASHTAG);
		
		// Send request
		return twitterService.getTopWordsForHashtag("#" + hashtag, 100);
		
	}
	
	@RequestMapping(value="/{hashtag}/{nrOfResults}", method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<TwitterWord> twitter(@PathVariable("hashtag") String hashtag, @PathVariable("nrOfResults") Integer nrOfResults) {
		
		// Input validation
		InputValidator.validate(hashtag, InputType.HASHTAG);
		InputValidator.validate(nrOfResults, InputType.POSITIVE_NUMBER);
		
		// Send request
		return twitterService.getTopWordsForHashtag("#" + hashtag, nrOfResults);
		
	}

}
