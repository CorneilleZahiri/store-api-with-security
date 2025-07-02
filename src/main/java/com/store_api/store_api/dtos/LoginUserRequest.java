package com.store_api.store_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserRequest {
    @NotBlank(message = "L'email est obligatoire.")
    @Email(message = "Le format de l'email est incorrecte.")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Size(min = 6, message = "Le mot de passe est de 6 caractères au moins.")
    private String password;
}
