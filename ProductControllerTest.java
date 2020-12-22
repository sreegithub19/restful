package com.adobe.prj.api;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.adobe.prj.entity.Product;
import com.adobe.prj.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@MockBean
	private OrderService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getProductsTest() throws Exception {
		List<Product> products = 
				Arrays.asList(new Product(1, "a", 500.00, 100), new Product(2, "b", 1500.00, 100));
		// mocking
		when(service.getAllProducts()).thenReturn(products);

		// @formatter:off
		mockMvc.perform(get("/api/products"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("a")))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("b")));
		// @formatter:on
		verify(service, times(1)).getAllProducts();
	}

	@Test
	public void addProductTest() throws Exception {
		Product p = new Product(0, "b", 1500.00, 100);
		Product p2 = new Product(1, "b", 1500.00, 100);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(p); // get JSON for Product p

		// mocking if Product type is passed to service he should return a PK 10
		when(service.addProduct(Mockito.any(Product.class))).thenReturn(p2);

		mockMvc.perform(post("/api/products")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		
		verify(service, times(1)).addProduct(Mockito.any(Product.class));

	}

	@Test
	public void addProductExceptionTest() throws Exception {
		Product p = new Product(0, "", -1500.0, 100);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(p); // get JSON for Product p

		mockMvc.perform(post("/api/products")
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errors", hasSize(2)))
				.andExpect(jsonPath("$.errors", hasItem("Give Name :-(")))
				.andExpect(jsonPath("$.errors", hasItem("Price -1500.0 should be more than 10")));

		verifyNoInteractions(service);

	}
}
