package com.example.demo.Repository;

import com.example.demo.entity.BuyerProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BuyerProfileRepository
        extends MongoRepository<BuyerProfile, String> {

    Optional<BuyerProfile> findByUserId(String userId);
}