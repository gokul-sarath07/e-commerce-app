package com.ecommerce.catalog.dao;

import com.ecommerce.catalog.model.Catalog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogDAO extends MongoRepository<Catalog, String> {

    List<Catalog> findByUsername(String username);
}
