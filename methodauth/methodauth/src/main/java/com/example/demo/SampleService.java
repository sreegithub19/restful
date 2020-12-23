package com.example.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
	
	@PreAuthorize("hasRole('ADMIN')")
	public String getUsernameInUpperCase() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
	    return securityContext.getAuthentication().getName();
	}
	
	 
}
