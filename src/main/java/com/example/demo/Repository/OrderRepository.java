package com.example.demo.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order,String> {

    List<Order> findByBuyerId(String buyerId);

    List<Order> findByProductId(String productId);

    List<Order> findByStatus(OrderStatus status);
}
