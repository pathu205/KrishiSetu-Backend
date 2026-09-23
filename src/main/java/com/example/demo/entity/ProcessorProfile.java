package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "processor-profiles")
public class ProcessorProfile {

    @Id
    private String id;

    private String userId;

    @NotBlank(message = "Business name is required")
    private String businessName;

    private String businessRegistrationNumber;

    private String businessType;

    @NotEmpty(message = "Processing activities are required")
    private List<ProcessingActivity> processingActivities;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Village or city is required")
    private String villageOrCity;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    private String gstNumber;
}