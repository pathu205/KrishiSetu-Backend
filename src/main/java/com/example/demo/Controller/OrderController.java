package com.example.demo.Controller;

import com.example.demo.Service.OrderService;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //Create order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order savedOrder = orderService.createOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    //Get All Orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        List<Order> allOrders = orderService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }

    //Get By Id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {

        Optional<Order> orderById = orderService.getOrderById(id);

        if (orderById.isPresent()) {
            return ResponseEntity.ok(orderById.get());
        }

        return ResponseEntity.notFound().build();
    }

    //Get Order By Buyer
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Order>> getOrderByBuyer(@PathVariable String buyerId){
        List<Order> orderByBuyerId = orderService.getOrderByBuyerId(buyerId);
        return ResponseEntity.ok(orderByBuyerId);
    }

    // Get Orders By Product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Order>> getOrdersByProduct(
            @PathVariable String productId) {

        return ResponseEntity.ok(orderService.getOrderByProduct(productId));
    }

    // Get Orders By Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(
            @PathVariable OrderStatus status) {

        return ResponseEntity.ok(
                orderService.getOrdersByStatus(status)
        );
    }

    // Update Order Status
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable String id,
            @RequestParam OrderStatus status) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(id, status)
        );
    }

    // Delete Order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable String id) {

        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }



}
