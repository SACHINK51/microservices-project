package com.microservice.prod_service.product_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.microservice.prod_service.product_service.dto.ProductRequest;
import com.microservice.prod_service.product_service.dto.ProductResponse;
import com.microservice.prod_service.product_service.repository.ProductRepository;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	public static MySQLContainer<?> mySQLDBContainer = new MySQLContainer<>("mysql:8.0.33");
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ProductRepository productRepository;
	
	@DynamicPropertySource
	public static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", mySQLDBContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mySQLDBContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mySQLDBContainer::getPassword);
	}
	
	@Test
	void shouldCreateProduct() throws Exception{
		
		ProductRequest productRequest = getProductRequest();
		String prodRequest = objectMapper.writeValueAsString(productRequest);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(prodRequest))
			.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder().name("Sachin").description("Smarty").price(1200).build();
	}

	@Test
	void shouldgetAllProducts() throws Exception{
		ProductResponse prodResponse = getProductResponse();
		String prodRequest = objectMapper.writeValueAsString(prodResponse);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(prodRequest))
		.andExpect(status().isOk());
	}

	private ProductResponse getProductResponse() {
		return ProductResponse.builder().id((long)123L).name("Sachin").description("Smarty").price(1220).build();
	}

}
