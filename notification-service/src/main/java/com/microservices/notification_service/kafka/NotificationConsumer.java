package com.microservices.notification_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.notification_service.model.OrderPlacedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final ObjectMapper objectMapper;

    public NotificationConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order-placed-topic", groupId = "notificationGroup")
    public void handleOrderPlaced(String message) {
        try {
            OrderPlacedEvent event = objectMapper.readValue(message, OrderPlacedEvent.class);
            System.out.println("Notification received: Order ID = " + event.getOrderId()
                    + ", Order Number = " + event.getOrderNumber()
                    + ", Status = " + event.getStatus());
        } catch (JsonProcessingException e) {
            System.err.println("Failed to parse event: " + e.getMessage());
        }
    }
}