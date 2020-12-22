package com.adobe.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adobe.prj.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, String> {
	@Query("select c from Customer c where c.firstName like %:#{[0]}% and c.firstName like %:firstName%")
	List<Customer> findByFirstName(@Param("firstName") String fn);
}
