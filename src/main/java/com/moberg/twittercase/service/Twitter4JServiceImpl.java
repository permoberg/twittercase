package com.moberg.twittercase.service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.moberg.twittercase.exceptions.InternalServerException;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.Query.Unit;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


@Service
public class Twitter4JServiceImpl implements Twitter4JService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static ExecutorService fixedPool;
	private Twitter twitter;
	
	@Value("${twitter.latitude}")
    private Double latitude;
	
	@Value("${twitter.longitude}")
    private Double longitude;
	
	@Value("${twitter.radius}")
    private Double radius;
	
	@Value("${twitter.count}")
    private Integer count;
	
	@Value("${twitter.access.token}")
    private String twitterToken;
	
	@Value("${twitter.access.secret}")
    private String twitterSecret;
	
	@Value("${twitter.consumer.key}")
    private String twitterConsumerKey;
	
	@Value("${twitter.consumer.secret}")
    private String twitterConsumerSecret;
	
	@Value("${twitter.request.pool.size}")
    private Integer poolSize;
	
	
	@PostConstruct
	private void initService() {
		try {
			twitter = new TwitterFactory().getInstance();
			AccessToken accessToken = new AccessToken(twitterToken, twitterSecret);
		    twitter.setOAuthConsumer(twitterConsumerKey, twitterConsumerSecret);
		    twitter.setOAuthAccessToken(accessToken);
		    fixedPool = Executors.newFixedThreadPool(poolSize);
		} catch(Exception e) {
			logger.error("Failed to initilize twitter authentication", e);
			throw e;
		}
	}
	
	@Override
	public List<Status> sendQuery(Query query) {
		try {
			
			// Thread to do the twitter request
			Callable<List<Status>> twitterCallable = new Callable<List<Status>>(){
		           
	            @Override
	            public List<Status> call() throws Exception {
	            	query.setGeoCode(new GeoLocation(latitude, longitude), radius, Unit.km);
	            	query.setCount(count);
	            	return twitter.search(query).getTweets();
	            }
	        };
			
		    Future<List<Status>> twitterFuture = fixedPool.submit(twitterCallable);
		    return twitterFuture.get();
			
	    } catch (InterruptedException | ExecutionException e) {
	    	logger.error("Exception thrown from twitter", e);
	    	throw new InternalServerException(e);
	    }

	}

}
