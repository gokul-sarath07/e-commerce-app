package com.ecommerce.product.service;

import com.ecommerce.product.dao.ProductDAO;
import com.ecommerce.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;


    public void addProduct(Product product) {
        productDAO.saveAndFlush(product);
    }

    public void updateProduct(Product product) {
        productDAO.save(product);
    }

    public void deleteProductWithId(Product product) {
        productDAO.delete(product);
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public Optional<Product> getProductWithId(long id) {
        return productDAO.findById(id);
    }
}
