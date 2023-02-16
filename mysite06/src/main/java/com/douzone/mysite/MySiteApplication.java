package com.douzone.mysite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.douzone.mysite.event.ApplicationContextEventListener;

@SpringBootApplication
public class MySiteApplication {
	
	/* spring-servlet.xml, Application Context Event Listener */	
	@Bean
	public ApplicationContextEventListener applicationContextEventListener() {
		return new ApplicationContextEventListener();
	}

	public static void main(String[] args) {	
		SpringApplication.run(MySiteApplication.class, args);
	}
}
