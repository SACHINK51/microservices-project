package com.microservice.order_service.order_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.microservice.order_service.order_service.model.OrderPlacedEvent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.microservice.order_service.order_service.config.WebClientConfig;
import com.microservice.order_service.order_service.dto.InventoryResponse;
import com.microservice.order_service.order_service.dto.OrderLineItemsDto;
import com.microservice.order_service.order_service.dto.OrderRequest;
import com.microservice.order_service.order_service.model.Order;
import com.microservice.order_service.order_service.model.OrderLineItems;
import com.microservice.order_service.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	//private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
	private final KafkaMessagePublisher orderPublisher;
	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;

	public String createOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList().stream()
				.map(this::mapToDto)
				.toList();
		order.setOrderLineItemsList(orderLineItemsList);

		List<String> skuCodes = order.getOrderLineItemsList().stream()
				.map(OrderLineItems::getSkuCode)
				.toList();

		InventoryResponse[] inventoryResponseArray = webClientBuilder.build()
				.get()
				.uri("lb://INVENTORY-SERVICE/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
				.retrieve()
				.bodyToMono(InventoryResponse[].class)
				.block();

		boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
				.allMatch(InventoryResponse::isInStock);

		if (allProductsInStock) {
			orderRepository.save(order);


			OrderPlacedEvent event = new OrderPlacedEvent(
					order.getOrderID(),
					order.getOrderNumber(),
					"PLACED"
			);

			//kafkaTemplate.send("order-events", event);
			orderPublisher.sendOrderPlacedEvent(event);
			System.out.println("Published order event to Kafka for order ID: " + order.getOrderID());
		} else {
			System.out.println("Product is not in stock");
		}
		return "Order placed successfully!";
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setId(orderLineItemsDto.getId());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		return orderLineItems;
	}
}
