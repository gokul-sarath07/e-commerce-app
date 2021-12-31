package com.ecommerce.order.model;

import lombok.Data;

@Data
public class Catalog {

    private String username;
    private Product product;
    private int count;
}

