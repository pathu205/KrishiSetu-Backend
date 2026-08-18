package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String name;              // Ragi, Bajra, Jowar, etc.
    private String category;          // Raw Millet, Flour, Snacks, etc.
    private String description;

    private double price;
    private double quantity;
    private String unit;              // KG, QUINTAL, TONNE

    private String sellerId;          // User/FPO/SHG/Processor ID

    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
