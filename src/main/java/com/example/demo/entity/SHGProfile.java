package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "shg-profiles")
public class SHGProfile {

    @Id
    private String id;

    private String userId;

    @NotBlank(message = "SHG name is required")
    private String shgName;

    private String groupRegistrationNumber;

    @NotBlank(message = "Village is required")
    private String village;

    @NotBlank(message = "Taluka is required")
    private String taluka;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    @Positive(message = "Number of members must be greater than 0")
    private int numberOfMembers;

    private SHGActivity primaryActivity;
}