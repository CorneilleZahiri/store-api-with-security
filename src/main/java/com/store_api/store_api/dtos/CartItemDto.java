package com.store_api.store_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CartItemDto {
    private CartProductsDto product;
    private int quantity;
    private BigDecimal totalPrice;
}
