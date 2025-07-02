package com.store_api.store_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserPassword {
    private String oldPassword;
    private String newPassword;
}
