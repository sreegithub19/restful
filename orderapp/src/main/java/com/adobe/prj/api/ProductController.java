package com.adobe.prj.api;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/products")
@Api(value = "Product Controller API", tags = " serves product")
public class ProductController {

	
	@Autowired
	private OrderService service;
	
	// http://localhost:8080/api/products
	// http://localhost:8080/api/products?price=100
	@ApiOperation(value = "gets all products")
	@GetMapping()
	public @ResponseBody List<Product> 
		getProducts(@RequestParam(name = "price", defaultValue = "0.0") double price) {
		return service.getAllProducts();
	}
	
	// http://localhost:8080/api/products/3
	@GetMapping("/{id}")
	public @ResponseBody Product getProduct(@PathVariable("id") int id) {
		try {
			return service.getProduct(id);
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Product with " + id + " not available");
		}
	}
 
	@PostMapping()
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product p) {
		service.addProduct(p);
		return new ResponseEntity<Product>(p, HttpStatus.CREATED); // 201
	}
	
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updateProduct(@PathVariable("id") int id, @RequestBody Product p) {
		service.updateProduct(id, p.getQuantity());
		return "product updated";
	}
	
}