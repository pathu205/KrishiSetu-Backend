package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "farmer-profiles")
public class FarmerProfile {

    @Id
    private String id;

    private String userId;

    @NotBlank(message = "Village is required")
    private String village;

    @NotBlank(message = "Taluka is required")
    private String taluka;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    @Positive(message = "Farm size must be greater than 0")
    private double farmSize;

    private FarmSizeUnit farmSizeUnit;

    @NotEmpty(message = "At least one millet crop is required")
    private List<MilletType> milletCrops;
}