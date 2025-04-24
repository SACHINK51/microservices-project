package com.microservice.order_service.order_service.dto;

import java.math.BigDecimal;
import java.util.List;

import com.microservice.order_service.order_service.model.OrderLineItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemsDto {
	private Long id;
	private String skuCode;
	private BigDecimal price;
	private Integer quantity;
}
