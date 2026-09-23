package com.example.demo.Controller;

import com.example.demo.Service.ProductService;
import com.example.demo.entity.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //Create product
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        product.setSellerId(user.getId());
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    //Get Product
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //Get Product By ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Product>> getProductBySellerId(
            @PathVariable String sellerId) {

        return ResponseEntity.ok(
                productService.getProductsBySeller(sellerId)
        );
    }

    // Get Products by Category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(
                productService.getProductsByCategory(category)
        );
    }

    //Get Product By Name
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam String name) {

        return ResponseEntity.ok(
                productService.searchProductsByName(name)
        );
    }

    // Get Active Products
    @GetMapping("/active")
    public ResponseEntity<List<Product>> getActiveProducts() {

        return ResponseEntity.ok(
                productService.getActiveProducts()
        );
    }

    // Update Product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @RequestBody Product product,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Product updatedProduct =
                productService.updateProduct(id, product,user.getId());

        return ResponseEntity.ok(updatedProduct);
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable String id,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        productService.deleteProduct(id, user.getId());

        return ResponseEntity.noContent().build();
    }



}
