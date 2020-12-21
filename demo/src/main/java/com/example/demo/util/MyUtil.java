package com.example.demo.util;

public class MyUtil {
	private String url;
	private int port;

	public MyUtil(String url, int port) {
		super();
		this.url = url;
		this.port = port;
	}

	public void doTask() {
		System.out.println("util Task !!!" + url + ": " + port);
	}

}
