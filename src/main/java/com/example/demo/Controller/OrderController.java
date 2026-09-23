package com.example.demo.Controller;

import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    //Create order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order, Authentication authentication){
        User user = (User) authentication.getPrincipal();

        Order savedOrder = orderService.createOrder(order,user.getId());
        return ResponseEntity.ok(savedOrder);
    }

    //Get All Orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        // Only ADMIN can view all orders
        if (user.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).build();
        }

        List<Order> allOrders = orderService.getAllOrders();

        return ResponseEntity.ok(allOrders);
    }

    //Get By Id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Optional<Order> orderOptional = orderService.getOrderById(id);

        if (orderOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderOptional.get();

        // ADMIN can view any order
        if (user.getRole() == Role.ADMIN) {
            return ResponseEntity.ok(order);
        }

        // BUYER can view their own order
        if (order.getBuyerId().equals(user.getId())) {
            return ResponseEntity.ok(order);
        }

        // SELLER can view an order for their own product
        Optional<Product> productOptional =
                productService.getProductById(order.getProductId());

        if (productOptional.isPresent()) {

            Product product = productOptional.get();

            if (product.getSellerId().equals(user.getId())) {
                return ResponseEntity.ok(order);
            }
        }


        // User has no permission
        return ResponseEntity.status(403).build();
    }

    //Get Order By Buyer
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Order>> getOrderByBuyer(@PathVariable String buyerId , Authentication authentication){

        User user = (User) authentication.getPrincipal();

        // ADMIN can view any buyer's orders
        if (user.getRole() == Role.ADMIN) {
            return ResponseEntity.ok(
                    orderService.getOrderByBuyerId(buyerId)
            );
        }

        // User can view only their own orders
        if (!user.getId().equals(buyerId)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(orderService.getOrderByBuyerId(buyerId));
    }

    // Get Orders By Product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Order>> getOrdersByProduct(
            @PathVariable String productId,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        // ADMIN can view orders for any product
        if (user.getRole() == Role.ADMIN) {
            return ResponseEntity.ok(
                    orderService.getOrderByProduct(productId)
            );
        }

        // Find the product
        Optional<Product> productOptional =
                productService.getProductById(productId);

        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();

        // Seller can view orders for their own product
        if (!product.getSellerId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                orderService.getOrderByProduct(productId)
        );
    }

    // Get Orders By Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(
            @PathVariable OrderStatus status,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        // Only ADMIN can view all orders by status
        if (user.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                orderService.getOrdersByStatus(status)
        );
    }

    // Get Orders by Seller
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Order>> getOrdersBySeller(
            @PathVariable String sellerId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        // ADMIN can view any seller's orders
        if (user.getRole() == Role.ADMIN) {
            return ResponseEntity.ok(
                    orderService.getOrdersBySeller(sellerId)
            );
        }

        // Seller can view only their own orders
        if (!user.getId().equals(sellerId)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                orderService.getOrdersBySeller(sellerId)
        );
    }

    // Update Order Status
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable String id,
            @RequestParam OrderStatus status,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Optional<Order> orderOptional =
                orderService.getOrderById(id);

        if (orderOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderOptional.get();

        // ADMIN can update any order
        if (user.getRole() == Role.ADMIN) {

            return ResponseEntity.ok(
                    orderService.updateOrderStatus(id, status)
            );
        }

        // Find the product associated with the order
        Optional<Product> productOptional =
                productService.getProductById(order.getProductId());

        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();

        // Only the seller of the product can update order status
        if (!product.getSellerId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                orderService.updateOrderStatus(id, status)
        );
    }

    // Delete Order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable String id,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Optional<Order> orderOptional =
                orderService.getOrderById(id);

        if (orderOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderOptional.get();

        // ADMIN can delete any order
        if (user.getRole() == Role.ADMIN) {

            orderService.deleteOrder(id);

            return ResponseEntity.noContent().build();
        }

        // Buyer can delete their own order
        if (order.getBuyerId().equals(user.getId())) {

            orderService.deleteOrder(id);

            return ResponseEntity.noContent().build();
        }

        // Seller can delete orders for their own product
        Optional<Product> productOptional =
                productService.getProductById(order.getProductId());

        if (productOptional.isPresent()) {

            Product product = productOptional.get();

            if (product.getSellerId().equals(user.getId())) {

                orderService.deleteOrder(id);

                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.status(403).build();
    }



}
