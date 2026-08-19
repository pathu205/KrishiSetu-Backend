package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @NotBlank
    private String name;              // Ragi, Bajra, Jowar, etc.
    @NotBlank
    private String category;          // Raw Millet, Flour, Snacks, etc.
    @NotBlank
    private String description;

    @Positive
    private double price;
    @Positive
    private double quantity;
    @NotBlank
    private String unit;              // KG, QUINTAL, TONNE
    @NotBlank
    private String sellerId;          // User/FPO/SHG/Processor ID

    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
