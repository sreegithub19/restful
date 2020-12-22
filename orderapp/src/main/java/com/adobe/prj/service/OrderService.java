package com.adobe.prj.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.prj.dao.CustomerDao;
import com.adobe.prj.dao.OrderDao;
import com.adobe.prj.dao.ProductDao;
import com.adobe.prj.entity.Customer;
import com.adobe.prj.entity.Item;
import com.adobe.prj.entity.Order;
import com.adobe.prj.entity.Product;

@Service
public class OrderService {
	@Autowired
	private ProductDao productDao;

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private OrderDao orderDao;

	@Transactional 
	public Customer addCustomer(Customer c) {
		return customerDao.save(c);
	}
	
	public List<Order> getOrders() {
		return orderDao.findAll();
	}

	@Transactional
	public Order addOrder(Order o) {
		List<Item> items = o.getItems();
		for (Item i : items) {
			Product p = productDao.findById(i.getProduct().getId()).get();
			if (i.getQty() > p.getQuantity())
				throw new RuntimeException();
			p.setQuantity(p.getQuantity() - i.getQty()); // dirty check happens and updates
		}
		return orderDao.save(o);
	}

	public List<Product> getAllProducts() {
		return productDao.findAll(); // select * from products
	}

	public Product getProduct(int id) {
		return productDao.findById(id).get(); // select * from products where id = id
	}

	@Transactional
	public Product addProduct(Product p) {
		return productDao.save(p); // insert sql
	}

	// dirty checking
	@Transactional
	public Product updateProduct(int id, int qty) {
		Product p = productDao.findById(id).get(); // qty = 100 ==> NoSuchElementException
		p.setQuantity(qty); // product became dirty
		return p;
	}

	public List<Customer> getCustomersByFirstName(String string) {
		return customerDao.findByFirstName(string);
	}

}