package com.microservice.prod_service.product_service.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import com.microservice.prod_service.product_service.model.Product;
import com.microservice.prod_service.product_service.dto.ProductRequest;
import com.microservice.prod_service.product_service.dto.ProductResponse;
import com.microservice.prod_service.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription()).price(productRequest.getPrice()).build();
		productRepository.save(product);
		log.info("Product {} is saved", product.getId());
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> products =  productRepository.findAll();
		return products.stream().map(this::mapToProductResponse).toList();
	}

	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder().id(product.getId()).name(product.getName()).description(product.getDescription()).price(product.getPrice()).build();
	}
}
