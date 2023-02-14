package com.douzone.mysite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.douzone.mysite.config.web.FileuploadConfig;
import com.douzone.mysite.config.web.MessageSourceConfig;
import com.douzone.mysite.config.web.MvcConfig;
import com.douzone.mysite.config.web.SecurityConfig;
import com.douzone.mysite.event.ApplicationContextEventListener;
import com.douzone.mysite.interceptor.SiteInterceptor;

@Configuration
@EnableAspectJAutoProxy /* spring-servlet, auto proxy */
/* spring-servlet.xml, context:annotation-config, context:component-scan */
@ComponentScan({"com.douzone.mysite.controller"})
@Import({MvcConfig.class, SecurityConfig.class, MessageSourceConfig.class, FileuploadConfig.class})
public class WebConfig implements WebMvcConfigurer {
	/* spring-servlet, Site Inteceptor */	
	@Bean
	public HandlerInterceptor siteInterceptor() {
		return new SiteInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(siteInterceptor())
			.addPathPatterns("/**");
	}
	
	/* spring-servlet.xml, Application Context Event Listener */	
	@Bean
	public ApplicationContextEventListener applicationContextEventListener() {
		return new ApplicationContextEventListener();
	}
}