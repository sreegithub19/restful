package com.adobe;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

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
		
	}
	
 
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}
/*
	@Autowired
	private RestTemplate template;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Override
	public void run(String... args) throws Exception {
		List<Product> products = Arrays.asList(new Product(0, "iPhone 12", 89000.00, 100),
				new Product(0, "Reynold Pen", 15.00, 100), new Product(0, "Camlin Marker", 50, 100));

		List<Customer> customers = Arrays.asList(new Customer("a@adobe.com", "Anna"),
				new Customer("b@adobe.com", "Bharath"));

		for (Product p : products) {
			service.addProduct(p);
		}

		for (Customer c : customers) {
			service.addCustomer(c);
		}

//		getProduct();
//		getAllProducts();
//		addProduct();
	}

	private void getAllProducts() {
		String result = template.getForObject("http://localhost:8080/api/products", String.class);

		System.out.println("**********");
		System.out.println(result);

		System.out.println("********");

		ResponseEntity<List<Product>> productsResponse = 
				template.exchange("http://localhost:8080/api/products",
						HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
				});

		List<Product> products = productsResponse.getBody();
		for (Product p : products) {
			System.out.println(p.getName());
		}
	}

	public void addProduct() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Product p = new Product();
		p.setName("Dummy");
		p.setPrice(1000);
		p.setQuantity(100);

		// HttpEntity<Product> requestEntity = new HttpEntity<>(p, headers);
		ResponseEntity<Product> productResponse = template.postForEntity("http://localhost:8080/api/products", p,
				Product.class);
		System.out.println(productResponse.getStatusCodeValue());
		System.out.println(productResponse.getBody().getId());

	}

	public void getProduct() {
		ResponseEntity<Product> productResponse = template.getForEntity("http://localhost:8080/api/products/1",
				Product.class);
		System.out.println(productResponse.getStatusCodeValue());
		System.out.println(productResponse.getBody().getName());
	}
*/
}