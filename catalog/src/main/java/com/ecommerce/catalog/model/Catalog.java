package com.ecommerce.catalog.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "catalogs")
public class Catalog {

    private String username;
    private Product product;
    private int count;
}
