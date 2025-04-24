package com.microservice.order_service.order_service.dto;

import java.util.List;

import com.microservice.order_service.order_service.model.OrderLineItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
	private List<OrderLineItemsDto> orderLineItemsDtoList;
}
