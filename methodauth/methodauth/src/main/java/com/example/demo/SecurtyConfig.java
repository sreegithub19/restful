package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurtyConfig extends WebSecurityConfigurerAdapter {


	@Bean
	public UserDetailsService userDetailsService() {
		// secret is the pwd
		UserBuilder users = User.builder().passwordEncoder(p -> "{bcrypt}" + p);
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(users.username("banu")
								.password("$2y$12$nqq0sEz29u8jBv50/azXseQqUCL231UQavkWVId/470xHUOghaYOq")
								.roles("USER").build());
		manager.createUser(users.username("admin")
								.password("$2y$12$nqq0sEz29u8jBv50/azXseQqUCL231UQavkWVId/470xHUOghaYOq")
								.roles("ADMIN","USER").build());
		return manager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().and().authorizeRequests().anyRequest().authenticated();
	}

}

