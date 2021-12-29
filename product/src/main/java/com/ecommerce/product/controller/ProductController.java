package com.ecommerce.product.controller;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return new ResponseEntity<>("Product has been saved.", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
        return new ResponseEntity<>("Product details has been updated.", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductWithId(@RequestBody Product product) {
        productService.deleteProductWithId(product);
        return new ResponseEntity<>("Product with ID: " + product.getId() + " has been deleted.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        if (productList.isEmpty()) { return new ResponseEntity<>("No products to show.", HttpStatus.OK); }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductWithId(@PathVariable long id) {
        Optional<Product> productOptional = productService.getProductWithId(id);
        if(productOptional.isEmpty()) { return new ResponseEntity<>("No product with ID: " + id, HttpStatus.OK); }
        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
    }
}
