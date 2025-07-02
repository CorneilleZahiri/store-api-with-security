package com.store_api.store_api.mappers;

import com.store_api.store_api.dtos.CartDto;
import com.store_api.store_api.dtos.CartItemDto;
import com.store_api.store_api.entities.Cart;
import com.store_api.store_api.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto cartItemToDto(CartItem cartItem);

}
