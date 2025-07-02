package com.store_api.store_api.mappers;

import com.store_api.store_api.dtos.RegisterUserRequest;
import com.store_api.store_api.dtos.UpdateUserRequest;
import com.store_api.store_api.dtos.UserDto;
import com.store_api.store_api.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-02T12:31:51+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();

        UserDto userDto = new UserDto( id, name, email );

        return userDto;
    }

    @Override
    public User userDtoToUser(RegisterUserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( userRequest.getName() );
        user.email( userRequest.getEmail() );
        user.password( userRequest.getPassword() );

        return user.build();
    }

    @Override
    public User updateUser(UpdateUserRequest userRequest, User user) {
        if ( userRequest == null ) {
            return user;
        }

        user.setName( userRequest.getName() );
        user.setEmail( userRequest.getEmail() );

        return user;
    }
}
