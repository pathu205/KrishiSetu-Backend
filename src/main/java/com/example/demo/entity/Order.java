package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collections;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String buyerId;

    private String productId;

    private double quantity;

    private String unit;

    private double pricePerUnit;

    private double totalPrice;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
