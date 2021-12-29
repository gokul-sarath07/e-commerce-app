package com.ecommerce.catalog.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class CatalogDTO {

    @Min(1)
    private Long productId;

    @Min(1)
    private int count;
}
