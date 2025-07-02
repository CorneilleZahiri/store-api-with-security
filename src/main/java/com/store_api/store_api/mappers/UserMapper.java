package com.store_api.store_api.mappers;

import com.store_api.store_api.dtos.RegisterUserRequest;
import com.store_api.store_api.dtos.UpdateUserRequest;
import com.store_api.store_api.dtos.UserDto;
import com.store_api.store_api.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToDto(User user);

    User userDtoToUser(RegisterUserRequest userRequest);

    User updateUser(UpdateUserRequest userRequest, @MappingTarget User user);
}

