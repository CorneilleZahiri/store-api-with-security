package com.store_api.store_api.mappers;

import com.store_api.store_api.dtos.CartDto;
import com.store_api.store_api.dtos.CartItemDto;
import com.store_api.store_api.dtos.CartProductsDto;
import com.store_api.store_api.entities.Cart;
import com.store_api.store_api.entities.CartItem;
import com.store_api.store_api.entities.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-02T12:31:51+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartDto toDto(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        UUID id = null;
        List<CartItemDto> items = null;

        id = cart.getId();
        items = cartItemSetToCartItemDtoList( cart.getItems() );

        BigDecimal totalPrice = cart.getTotalPrice();

        CartDto cartDto = new CartDto( id, items, totalPrice );

        return cartDto;
    }

    @Override
    public CartItemDto cartItemToDto(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartProductsDto product = null;
        int quantity = 0;

        product = productToCartProductsDto( cartItem.getProduct() );
        if ( cartItem.getQuantity() != null ) {
            quantity = cartItem.getQuantity();
        }

        BigDecimal totalPrice = cartItem.getTotalPrice();

        CartItemDto cartItemDto = new CartItemDto( product, quantity, totalPrice );

        return cartItemDto;
    }

    protected List<CartItemDto> cartItemSetToCartItemDtoList(Set<CartItem> set) {
        if ( set == null ) {
            return null;
        }

        List<CartItemDto> list = new ArrayList<CartItemDto>( set.size() );
        for ( CartItem cartItem : set ) {
            list.add( cartItemToDto( cartItem ) );
        }

        return list;
    }

    protected CartProductsDto productToCartProductsDto(Product product) {
        if ( product == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        BigDecimal price = null;

        id = product.getId();
        name = product.getName();
        price = product.getPrice();

        CartProductsDto cartProductsDto = new CartProductsDto( id, name, price );

        return cartProductsDto;
    }
}
