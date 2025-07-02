package com.store_api.store_api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateQuantityRequest {
    @NotNull(message = "La quantité ne doit pas être null")
    @Min(value = 1, message = "La quantité doit être supérieure à 0.")
    private Integer quantity;
}
