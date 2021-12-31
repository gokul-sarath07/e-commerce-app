package com.ecommerce.order.model;

import lombok.Data;

@Data
public class Order {

    private User user;
    private Catalog catalog;
    private double totalAmount;
}
