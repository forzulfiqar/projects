package com.userregspringrestangular.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



//@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = "com.javacodegeeks.examples.restfulbackend")
public class AppConfig extends WebMvcConfigurerAdapter {
	public static final String REST_API_ROOT = "/api/";
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry reg) {
		reg.addResourceHandler("/**").addResourceLocations("/");
	}

}
