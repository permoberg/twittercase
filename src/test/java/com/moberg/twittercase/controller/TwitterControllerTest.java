package com.moberg.twittercase.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.moberg.twittercase.exceptions.InternalServerException;
import com.moberg.twittercase.service.TwitterService;
import com.moberg.twittercase.service.TwitterWord;

@RunWith(SpringRunner.class)
@WebMvcTest(TwitterController.class)
public class TwitterControllerTest {

	private static final String HASHTAG = "myTag";
	private static final String INVALID_HASHTAG = "123456";
	private static final String WORD = "word";
	private static final int COUNT = 10;
	private static final int DEFAULT_NR_RESULT = 100;
	private static final int CUSTOM_NR_RESULT = 33;
	private static final String INVALID_CUSTOM_NR_RESULT = "invalid";
	
	@Autowired
    private MockMvc mockMvc;

	@MockBean
	private TwitterService twitterService;
	
    @Test
    public void twitterHappyCase() throws Exception {
        
    	when(twitterService.getTopWordsForHashtag("#" + HASHTAG, DEFAULT_NR_RESULT)).thenReturn(Arrays.asList(new TwitterWord(WORD, COUNT)));
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/twitter/" + HASHTAG).accept(MediaType.APPLICATION_JSON_UTF8);
    	
    	this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].word", is(WORD)))
    			.andExpect(jsonPath("$[0].count", is(COUNT)));
    }
    
    @Test
    public void twitterInternalServerError() throws Exception {
        
    	when(twitterService.getTopWordsForHashtag("#" + HASHTAG, DEFAULT_NR_RESULT)).thenThrow(new InternalServerException());
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/twitter/" + HASHTAG).accept(MediaType.APPLICATION_JSON_UTF8);
    	
    	this.mockMvc.perform(requestBuilder)
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    public void twitterInvalidInputError() throws Exception {
        
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/twitter/" + INVALID_HASHTAG).accept(MediaType.APPLICATION_JSON_UTF8);
    	
    	this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void twitterCustomCountHappyCase() throws Exception {
        
    	when(twitterService.getTopWordsForHashtag("#" + HASHTAG, CUSTOM_NR_RESULT)).thenReturn(Arrays.asList(new TwitterWord(WORD, COUNT)));
    	
    	RequestBuilder requestBuilder = 
    			MockMvcRequestBuilders.get("/twitter/" + HASHTAG + "/" + CUSTOM_NR_RESULT).accept(MediaType.APPLICATION_JSON_UTF8);
    	
    	this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].word", is(WORD)))
    			.andExpect(jsonPath("$[0].count", is(COUNT)));
    }
    
    @Test
    public void twitterCustomCountInternalServerError() throws Exception {
        
    	when(twitterService.getTopWordsForHashtag("#" + HASHTAG, CUSTOM_NR_RESULT)).thenThrow(new InternalServerException());
    	
    	RequestBuilder requestBuilder = 
    			MockMvcRequestBuilders.get("/twitter/" + HASHTAG + "/" + CUSTOM_NR_RESULT).accept(MediaType.APPLICATION_JSON_UTF8);
    	
    	this.mockMvc.perform(requestBuilder)
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    public void twitterCustomCountInvalidNrOfResults() throws Exception {
        
    	RequestBuilder requestBuilder = 
    			MockMvcRequestBuilders.get("/twitter/" + INVALID_HASHTAG + "/" + INVALID_CUSTOM_NR_RESULT).accept(MediaType.APPLICATION_JSON_UTF8);
    	
    	this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void twitterCustomCountInvalidHashtag() throws Exception {
        
    	RequestBuilder requestBuilder = 
    			MockMvcRequestBuilders.get("/twitter/" + INVALID_HASHTAG + "/" + CUSTOM_NR_RESULT).accept(MediaType.APPLICATION_JSON_UTF8);
    	
    	this.mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }
	
}
