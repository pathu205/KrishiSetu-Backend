package com.example.demo.Repository;

import com.example.demo.entity.ProcessorProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProcessorProfileRepository
        extends MongoRepository<ProcessorProfile, String> {

    Optional<ProcessorProfile> findByUserId(String userId);
}