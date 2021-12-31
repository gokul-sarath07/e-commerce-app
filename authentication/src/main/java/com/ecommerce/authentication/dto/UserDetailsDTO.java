package com.ecommerce.authentication.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UserDetailsDTO {
    private Map<String, Object> userMap;
}
