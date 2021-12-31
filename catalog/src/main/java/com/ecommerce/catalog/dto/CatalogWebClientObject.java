package com.ecommerce.catalog.dto;

import com.ecommerce.catalog.model.Catalog;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CatalogWebClientObject {
    private List<Catalog> catalogList;
}
