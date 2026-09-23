package com.example.demo.Repository;

import com.example.demo.entity.FPOProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FPOProfileRepository
        extends MongoRepository<FPOProfile, String> {

    Optional<FPOProfile> findByUserId(String userId);
}