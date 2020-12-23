package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

	
	@GetMapping("/")
	public String greeting() {
		return "Welcome";
	}
	
	@GetMapping("/user")
	public String greetingUser() {
		return "Welcome User";
	}
	
	@GetMapping("/admin")
	public String greetingAdmin() {
		return "Welcome Admin";
	}
}
