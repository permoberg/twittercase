package com.moberg.twittercase.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.moberg.twittercase.exceptions.InternalServerException;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.Query.Unit;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


@Service
public class Twitter4JServiceImpl implements Twitter4JService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Twitter twitter;
	
	@Autowired
	private Environment env;
	
	@PostConstruct
	private void initService() {
		try {
			twitter = new TwitterFactory().getInstance();
			AccessToken accessToken = new AccessToken(env.getProperty("twitter.access.token"), env.getProperty("twitter.access.secret"));
		    twitter.setOAuthConsumer(env.getProperty("twitter.consumer.key"), env.getProperty("twitter.consumer.secret"));
		    twitter.setOAuthAccessToken(accessToken);
		} catch(Exception e) {
			logger.error("Failed to initilize twitter authentication", e);
		}
	}
	
	@Override
	public List<Status> sendQuery(Query query) {
		try {
			setGeoLocation(query);
			setCount(query);
			return twitter.search(query).getTweets();
	    } catch (TwitterException e) {
	    	logger.error("Exception thrown from twitter", e);
	    	throw new InternalServerException(e);
	    }

	}
	
	private void setGeoLocation(Query query) {
		double latitud = Double.valueOf(env.getProperty("twitter.latitude"));
		double longitud = Double.valueOf(env.getProperty("twitter.longitude"));
		double radius = Double.valueOf(env.getProperty("twitter.radius"));
		query.setGeoCode(new GeoLocation(latitud, longitud), radius, Unit.km);
	}
	
	private void setCount(Query query) {
		int count = Integer.valueOf(env.getProperty("twitter.count"));
		query.setCount(count);
	}

}
