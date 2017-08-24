package com.moberg.twittercase.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import twitter4j.Query;
import twitter4j.Status;

public class TwitterServiceTest {

	private static final String HASHTAG = "hashtag";
	private static final String TEXT1 = "i a c c a c q";
	private static final String TEXT2 = "c a a q q a c";
	private static final String TEXT3 = "q q a a g q a";
	
	@Mock
	private Status tweetMock;
	
	@Mock
	private Twitter4JService twitter4jService;
	
	@InjectMocks
	private TwitterServiceImpl service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		// Double occurences due to nullcheck validation
		when(tweetMock.getText()).thenReturn(TEXT1, TEXT1, TEXT2, TEXT2, TEXT3, TEXT3);
		
		// Init list with 3 tweets
		List<Status> tweets =  Arrays.asList(tweetMock, tweetMock, tweetMock);
		when(twitter4jService.sendQuery(any(Query.class))).thenReturn(tweets);
		
	}
	
	@Test
	public void EmptyTweetList() {
		when(twitter4jService.sendQuery(any(Query.class))).thenReturn(new ArrayList<>());
		
		List<TwitterWord> result = service.getTopWordsForHashtag(HASHTAG, 10);
		
		assertThat(result.size(), is(0));
	
	}
	
	@Test
	public void moreNrOfResultsThanAvailable() {
		List<TwitterWord> result = service.getTopWordsForHashtag(HASHTAG, 100);
		
		assertThat(result.size(), is(5));
		assertThat(result.get(0).getWord(), is("a"));
		assertThat(result.get(0).getCount(), is(8));
		assertThat(result.get(1).getWord(), is("q"));
		assertThat(result.get(1).getCount(), is(6));
		assertThat(result.get(2).getWord(), is("c"));
		assertThat(result.get(2).getCount(), is(5));
		assertThat(result.get(3).getWord(), is("g"));
		assertThat(result.get(3).getCount(), is(1));
		assertThat(result.get(4).getWord(), is("i"));
		assertThat(result.get(4).getCount(), is(1));
		
	}
	
	@Test
	public void limitNrOfResultsToTwo() {
		List<TwitterWord> result = service.getTopWordsForHashtag(HASHTAG, 2);
		
		assertThat(result.size(), is(2));
		assertThat(result.get(0).getWord(), is("a"));
		assertThat(result.get(0).getCount(), is(8));
		assertThat(result.get(1).getWord(), is("q"));
		assertThat(result.get(1).getCount(), is(6));
		
	}
	
}
