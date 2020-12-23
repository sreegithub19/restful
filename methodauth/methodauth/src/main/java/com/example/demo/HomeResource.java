 package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

	@Autowired
	SampleService service;
	
	@GetMapping("/")
	public String greeting() {
		return "Welcome " + service.getUsernameInUpperCase();
	}
	
	@GetMapping("/user")
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	public String greetingUser() {
		return "Welcome User";
	}
	
	@GetMapping("/admin")
	@Secured("ROLE_ADMIN")
	public String greetingAdmin() {
		return "Welcome Admin";
	}
}

