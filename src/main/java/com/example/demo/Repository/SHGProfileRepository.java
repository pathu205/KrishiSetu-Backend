package com.example.demo.Repository;

import com.example.demo.entity.SHGProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SHGProfileRepository
        extends MongoRepository<SHGProfile, String> {

    Optional<SHGProfile> findByUserId(String userId);
}