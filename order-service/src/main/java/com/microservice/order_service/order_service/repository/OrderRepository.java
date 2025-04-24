package com.microservice.order_service.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.order_service.order_service.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
