package com.adobe;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adobe.prj.entity.Customer;
import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;

@SpringBootApplication
public class OrderappApplication implements CommandLineRunner {
	
	@Autowired
	private OrderService service;
	
	public static void main(String[] args) {
		SpringApplication.run(OrderappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		addCustomers();
//		addProducts();
//		getProducts();
		getCustomers();
	}

	private void getCustomers() {
		List<Customer> customers = service.getCustomersByFirstName("Bh__%m%");
		for(Customer c : customers) {
			System.out.println(c.getFirstName());
		}
	}

	private void getProducts() {
		List<Product> products = service.getAllProducts();
		for(Product p : products) {
			System.out.println(p.getName() + ", " + p.getPrice() + "," + p.getQuantity());
		}
	}

	private void addProducts() {
		List<Product> products = Arrays.asList(new Product(0, "iPhone 12", 89000.00, 100),
				new Product(0, "Reynold Pen", 15.00, 100), new Product(0, "Camlin Marker", 50, 100));

		for (Product p : products) {
			service.addProduct(p);
		}
	}

	private void addCustomers() {
		service.addCustomer(new Customer("a@adobe.com", "Ashok"));
		service.addCustomer(new Customer("b@adobe.com", "Bharath"));
		service.addCustomer(new Customer("ba@adobe.com", "Bheema"));
	}
	
	

}
