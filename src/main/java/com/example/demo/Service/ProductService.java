package com.example.demo.Service;

import com.example.demo.exception.ForbiddenException;
import com.example.demo.entity.Product;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Create Product
    public Product createProduct(Product product) {

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setActive(true);

        return productRepository.save(product);
    }

    // Get all Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get Product by ID
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    // Get Products by Seller
    public List<Product> getProductsBySeller(String sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    // Get Products by Category
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    // Search Products by Name
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // Get Active Products
    public List<Product> getActiveProducts() {
        return productRepository.findByActiveTrue();
    }

    // Update Product
    public Product updateProduct(String id, Product product, String userId) {

        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        Product oldProduct = existingProduct.get();

        if (!oldProduct.getSellerId().equals(userId)){
            throw new ForbiddenException("You are not allowed to update this product");
        }

        if (product.getName() != null) {
            oldProduct.setName(product.getName());
        }

        if (product.getCategory() != null) {
            oldProduct.setCategory(product.getCategory());
        }

        if (product.getDescription() != null) {
            oldProduct.setDescription(product.getDescription());
        }

        if (product.getPrice() > 0) {
            oldProduct.setPrice(product.getPrice());
        }

        if (product.getQuantity() >= 0) {
            oldProduct.setQuantity(product.getQuantity());
        }

        if (product.getUnit() != null) {
            oldProduct.setUnit(product.getUnit());
        }

        oldProduct.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(oldProduct);
    }

    // Delete Product
    public void deleteProduct(String id,String userId) {

        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isEmpty()){
            throw new RuntimeException("Product not found");
        }
        Product product = existingProduct.get();

        if (!product.getSellerId().equals(userId)){
            throw new ForbiddenException(
                    "You are not allowed to delete this product"
            );
        }

        productRepository.deleteById(id);
    }
}