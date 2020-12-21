package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.util.MyUtil;

@Configuration
public class MyConfiguration {
	
	@Bean
	public MyUtil getUtil() {
		return new MyUtil("localhost", 1234);
	}
}
