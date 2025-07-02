package com.store_api.store_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    //    @JsonIgnore => Pour ignorer
    private Long id;
    //    @JsonProperty("nom") => Pour renommer
    private String name;
    private String email;
}
