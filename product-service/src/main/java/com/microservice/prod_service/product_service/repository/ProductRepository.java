package com.microservice.prod_service.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.prod_service.product_service.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
