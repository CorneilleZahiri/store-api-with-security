package com.store_api.store_api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterProductCartRequest {
    @NotNull(message = "Le produit ne doit pas être nul")
    private Long productId;
}
