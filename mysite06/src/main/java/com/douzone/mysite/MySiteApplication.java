package com.douzone.mysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class MySiteApplication {

	@Controller
	public class TestController {
		
		@ResponseBody
		@RequestMapping("/hi")
		public String test() {
			return "hiihizzzzzzzzzz";
		}
	}
	
	public static void main(String[] args) {	
		SpringApplication.run(MySiteApplication.class, args);
	}
}
