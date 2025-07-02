package com.store_api.store_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class RegisterProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Byte categoryId;
}
