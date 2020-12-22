package com.adobe;

import com.adobe.prj.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Product p = new Product(33,"a",445.55,100);
		mapper.writeValue(System.out, p);
		 
	}

}
