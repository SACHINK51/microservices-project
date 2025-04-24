package com.microservice.order_service.order_service.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.order_service.order_service.dto.OrderRequest;
import com.microservice.order_service.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
	//@TimeLimiter(name = "inventory")
	public CompletableFuture<String> createOrder(@RequestBody OrderRequest orderRequest) {
		return CompletableFuture.supplyAsync(() -> orderService.createOrder(orderRequest));
	}

	public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
		return CompletableFuture.supplyAsync(() -> "OOPS! Something went wrong, please order after sometime!");
	}
}
