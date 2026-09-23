package com.example.demo.Repository;

import com.example.demo.entity.FarmerProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FarmerProfileRepository extends MongoRepository<FarmerProfile,String> {
    Optional<FarmerProfile> findByUserId(String userId);
}
