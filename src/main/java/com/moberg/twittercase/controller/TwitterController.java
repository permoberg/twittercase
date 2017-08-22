package com.moberg.twittercase.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

	@RequestMapping(method = RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Response twitter() {
        return new Response(10, "Name");
    }


	public class Response implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private int id;
		private String name;
		
		public Response() {};
		
		public Response(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
}


