package com.store_api.store_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", nullable = false, insertable = false, updatable = false)
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<CartItem> items = new LinkedHashSet<>();

    //Calculer le totalPrice
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem item : this.getItems()) {
            totalPrice = totalPrice.add(item.getTotalPrice());
        }

        return totalPrice;
    }

    public CartItem getCartItem(Long productId) {
        return this.getItems().stream()
                .filter(cartItem -> cartItem.getProduct()
                        .getId().equals(productId)).findFirst().orElse(null);
    }

    public CartItem addItem(Product product) {
        //Rechercher le produit dans le panier
        CartItem cartItem = getCartItem(product.getId());

        if (cartItem == null) {
            cartItem = new CartItem();

            cartItem.setCart(this);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            this.getItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }

        return cartItem;
    }

    public void removeCartItem(Long productId) {
        //Rechercher le CartItem Correspondant
        CartItem cartItem = this.getCartItem(productId);
        //Supprimer le CartItem du Cart
        if (cartItem != null) {
            this.getItems().remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void clearItem() {
        this.getItems().clear();
    }
}
