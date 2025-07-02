package com.store_api.store_api.service;

import com.store_api.store_api.dtos.CartDto;
import com.store_api.store_api.dtos.CartItemDto;
import com.store_api.store_api.entities.Cart;
import com.store_api.store_api.entities.CartItem;
import com.store_api.store_api.entities.Product;
import com.store_api.store_api.exceptions.CartNotFoundException;
import com.store_api.store_api.exceptions.ProductNotFoundException;
import com.store_api.store_api.mappers.CartMapper;
import com.store_api.store_api.repositories.CartRepository;
import com.store_api.store_api.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartDto createCart() {
        Cart cart = cartRepository.save(new Cart());
        //Le convertir en Dto
        return cartMapper.toDto(cart);
    }

    public CartItemDto addProductToCart(UUID cartId, Long ProductId) {
        //Rechercher le Panier
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        //Rechercher le produit
        Product product = productRepository.findById(ProductId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        //Ajouter un nouveau CartItem avec quantité 1 si inexistant
        CartItem cartItem = cart.addItem(product);

        //Enregistrer le panier (le CartItem sera enregistré automatiquement
        cartRepository.save(cart);

        return cartMapper.cartItemToDto(cartItem);
    }

    public CartDto getCart(UUID cartId) {
        //Rechercher le Panier
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        return cartMapper.toDto(cart);
    }

    public CartItemDto updateCartItem(UUID cartId, Long productId, Integer quantity) {
        //Rechercher le Panier
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        //Rechercher le CartItem Correspondant
        CartItem cartItem = cart.getCartItem(productId);

        if (cartItem != null) {
            cartItem.setQuantity(quantity);
        } else {
            throw new ProductNotFoundException();
        }

        cartRepository.save(cart);

        return cartMapper.cartItemToDto(cartItem);
    }

    public void deleteCartItem(UUID cartId, Long productId) {
        //Rechercher le Panier
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        //Supprimer le CartItem du Cart
        cart.removeCartItem(productId);

        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        //Rechercher le Panier
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        //Vider le panier
        cart.clearItem();
        cartRepository.save(cart);
    }
}
