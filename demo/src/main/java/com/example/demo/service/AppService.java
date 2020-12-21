package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeDao;

@Service
public class AppService {
	@Autowired
	private EmployeeDao employeeDao;
	
	public void insert() {
		employeeDao.addEmployee();
	}
}
