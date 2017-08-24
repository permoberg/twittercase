package com.moberg.twittercase.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.moberg.twittercase.service.TwitterService;

@RunWith(SpringRunner.class)
@WebMvcTest(TwitterController.class)
public class TwitterControllerTest {

	private static final String HASHTAG = "myTag";
	private static final int DEFAULT_NR_RESULT = 100;
	
	@Autowired
    private MockMvc mockMvc;

	@MockBean
	private TwitterService twitterService;
	
    @Test
    public void testSayHelloWorld() throws Exception {
        
//    	when(twitterService.getTopWordsForHashtag(HASHTAG, DEFAULT_NR_RESULT)).thenReturn(ArrayList<TwitterWord>());
//    	
//    	
//    	MockMvcRequestBuilders.get("/twitter/weather")
//    	
//    	this.mockMvc.perform(get("/").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"));

    }
	
}
