package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;

import com.example.demo.service.AppService;
import com.example.demo.util.MyUtil;

@SpringBootApplication(exclude = AopAutoConfiguration.class)
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private AppService service;
	
	@Autowired
	MyUtil util;
	public static void main(String[] args) {
		/* ApplicationContext ctx = */ SpringApplication.run(DemoApplication.class, args);
		
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("app.xml");	
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//		ctx.scan("com.example");
//		ctx.refresh();
		/*
		 * String[] names = ctx.getBeanDefinitionNames(); for(String name : names) {
		 * System.out.println(name); }
		 * 
		 * AppService service = ctx.getBean("appService", AppService.class);
		 * service.insert();
		 */
	}

	@Override
	public void run(String... args) throws Exception {
		service.insert();
		util.doTask();
	}

}
