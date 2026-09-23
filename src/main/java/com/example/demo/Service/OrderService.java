package com.example.demo.Service;

import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // Create Order
    public Order createOrder(Order order, String buyerId) {
        // Set buyer from logged-in user
        order.setBuyerId(buyerId);

        // Find product
        Optional<Product> product = productRepository.findById(order.getProductId());

        if (product.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        Product existingProduct = product.get();
        // Check unit
        if (!existingProduct.getUnit().equalsIgnoreCase(order.getUnit())) {
            throw new RuntimeException("Unit does not match product unit");
        }

        // Check available quantity
        if (order.getQuantity() > existingProduct.getQuantity()) {
            throw new RuntimeException("Insufficient product quantity");
        }



        // Get actual price from Product
        order.setPricePerUnit(existingProduct.getPrice());

        // Calculate total price
        order.setTotalPrice(
                order.getQuantity() * order.getPricePerUnit()
        );

        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    //Get all orders
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    //Get all by Id
    public Optional<Order> getOrderById(String id){
        return orderRepository.findById(id);
    }

    //Get order by buyerId
    public List<Order> getOrderByBuyerId(String buyerId){
        return orderRepository.findByBuyerId(buyerId);
    }

    //Get order by product
    public List<Order> getOrderByProduct(String productId){
        return orderRepository.findByProductId(productId);
    }

    //Get Order By Status
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    // Get Orders by Seller
    public List<Order> getOrdersBySeller(String sellerId) {

        List<Product> sellerProducts =
                productRepository.findBySellerId(sellerId);

        List<Order> sellerOrders = new ArrayList<>();

        for (Product product : sellerProducts) {

            List<Order> orders =
                    orderRepository.findByProductId(product.getId());

            sellerOrders.addAll(orders);
        }

        return sellerOrders;
    }


    // Update Order Status
    // Update Order Status
    public Order updateOrderStatus(String id, OrderStatus status) {

        Optional<Order> existingOrder = orderRepository.findById(id);

        if (existingOrder.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        Order order = existingOrder.get();

        OrderStatus currentStatus = order.getStatus();

        // Check whether the status transition is allowed
        boolean validTransition = false;

        if (currentStatus == OrderStatus.PENDING &&
                (status == OrderStatus.CONFIRMED ||
                        status == OrderStatus.CANCELLED)) {

            validTransition = true;

        } else if (currentStatus == OrderStatus.CONFIRMED &&
                (status == OrderStatus.PROCESSING ||
                        status == OrderStatus.CANCELLED)) {

            validTransition = true;

        } else if (currentStatus == OrderStatus.PROCESSING &&
                status == OrderStatus.SHIPPED) {

            validTransition = true;

        } else if (currentStatus == OrderStatus.SHIPPED &&
                status == OrderStatus.DELIVERED) {

            validTransition = true;
        }

        if (!validTransition) {
            throw new RuntimeException(
                    "Invalid order status transition from "
                            + currentStatus + " to " + status
            );
        }

        // CONFIRM ORDER → reduce stock
        if (status == OrderStatus.CONFIRMED) {

            Optional<Product> existingProduct =
                    productRepository.findById(order.getProductId());

            if (existingProduct.isEmpty()) {
                throw new RuntimeException("Product not found");
            }

            Product product = existingProduct.get();

            if (order.getQuantity() > product.getQuantity()) {
                throw new RuntimeException("Insufficient product quantity");
            }

            product.setQuantity(
                    product.getQuantity() - order.getQuantity()
            );

            product.setUpdatedAt(LocalDateTime.now());

            productRepository.save(product);
        }

        // CANCEL ORDER → restore stock
        if (status == OrderStatus.CANCELLED &&
                currentStatus == OrderStatus.CONFIRMED) {

            Optional<Product> existingProduct =
                    productRepository.findById(order.getProductId());

            if (existingProduct.isEmpty()) {
                throw new RuntimeException("Product not found");
            }

            Product product = existingProduct.get();

            product.setQuantity(
                    product.getQuantity() + order.getQuantity()
            );

            product.setUpdatedAt(LocalDateTime.now());

            productRepository.save(product);
        }

        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    // Delete Order
    public void deleteOrder(String id) {

        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }

        orderRepository.deleteById(id);
    }


}
