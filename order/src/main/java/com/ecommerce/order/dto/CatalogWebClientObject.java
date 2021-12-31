package com.ecommerce.order.dto;

import com.ecommerce.order.model.Catalog;
import lombok.Data;

import java.util.List;

@Data
public class CatalogWebClientObject {
    private List<Catalog> catalogList;
}
