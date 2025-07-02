package com.store_api.store_api.controllers;

import com.store_api.store_api.dtos.CartDto;
import com.store_api.store_api.dtos.RegisterProductCartRequest;
import com.store_api.store_api.dtos.UpdateQuantityRequest;
import com.store_api.store_api.exceptions.CartNotFoundException;
import com.store_api.store_api.exceptions.ProductNotFoundException;
import com.store_api.store_api.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
@Tag(name = "Carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    @Operation(summary = "Créer un panier")
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriComponentsBuilder) {

        CartDto cartDto = cartService.createCart();

        //Générer le lien
        URI location = uriComponentsBuilder
                .path("/carts/{id}")
                .buildAndExpand(cartDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Ajouter un produit dans un panier")
    public ResponseEntity<?> addProductToCart(
            @Parameter(description = "Id du produit") @PathVariable UUID cartId,
            @Valid @RequestBody RegisterProductCartRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addProductToCart(cartId, request.getProductId()));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable("cartId") UUID cartId) {

        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCart(cartId));
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCartItem(@PathVariable("cartId") UUID cartId,
                                            @PathVariable("productId") Long productId,
                                            @Valid @RequestBody UpdateQuantityRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.updateCartItem(cartId, productId, request.getQuantity()));
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable("cartId") UUID cartId,
                                            @PathVariable("productId") Long productId) {

        cartService.deleteCartItem(cartId, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(@PathVariable("cartId") UUID cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound(CartNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Panier inexistant")
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound(ProductNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Produit inexistant dans le panier")
        );
    }
}
