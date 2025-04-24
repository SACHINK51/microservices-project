package com.microservice.order_service.order_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.order_service.order_service.model.OrderPlacedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessagePublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${order.topic.name:order-placed-topic}")
    private String topic;

    public KafkaMessagePublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendOrderPlacedEvent(OrderPlacedEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic, message);
            System.out.println("Sent OrderPlacedEvent: " + message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize OrderPlacedEvent", e);
        }
    }
}
