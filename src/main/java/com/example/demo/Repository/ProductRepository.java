package com.example.demo.Repository;


import com.example.demo.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {

    List<Product> findBySellerId(String sellerId);

    List<Product> findByCategory(String category);

    List<Product> findByActiveTrue();
}
