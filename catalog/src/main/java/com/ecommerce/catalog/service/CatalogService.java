package com.ecommerce.catalog.service;

import com.ecommerce.catalog.dao.CatalogDAO;
import com.ecommerce.catalog.dto.CatalogDTO;
import com.ecommerce.catalog.exception.CatalogException;
import com.ecommerce.catalog.model.Catalog;
import com.ecommerce.catalog.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CatalogService {

    private CatalogDAO catalogDAO;
    private WebClient webClientBuilder;

    @Autowired
    public CatalogService(CatalogDAO catalogDAO, WebClient webClientBuilder) {
        this.catalogDAO = catalogDAO;
        this.webClientBuilder = webClientBuilder;
    }

    public List<Catalog> getAllCatalog() {
        return catalogDAO.findAll();
    }

    public void addProductToCatalog(CatalogDTO catalogDTO) {
        String username = getUsernameOfLoggedInUser();
        if(username.contains(" ")) {
            throw new CatalogException("Please login and try again.");
        }

        Product product = getProductWithId(catalogDTO.getProductId());

        Catalog catalog = new Catalog();
        catalog.setUsername(username);
        catalog.setProduct(product);
        catalog.setCount(catalogDTO.getCount());

        catalogDAO.insert(catalog);
    }

    @Async
    private String getUsernameOfLoggedInUser() {
        return webClientBuilder.get()
                .uri("http://localhost:8080/api/auth/current-user")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Async
    private Product getProductWithId(long id) {
        return webClientBuilder.get()
                .uri("http://localhost:8081/api/product/" + id)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    public List<Catalog> getProductListWithUsername(String username) {
        List<Catalog> catalogList = catalogDAO.findByUsername(username);
        if(catalogList.isEmpty()) { throw new CatalogException("Catalog is empty."); }

        return catalogList;
    }

    public Map<String, Object> getUserListWithProductName(String productName) {
        List<Catalog> catalogList = catalogDAO.findAll();
        if(catalogList.isEmpty()) { throw new CatalogException("Catalog is empty."); }


        Set<String> userList = catalogList.stream()
                .filter(r -> r.getProduct().getName().equals(productName))
                .map(r -> r.getUsername()).collect(Collectors.toSet());

        Map<String, Object> map = new HashMap<>();
        map.put("product", productName);
        map.put("users", userList);

        return map;
    }
}
