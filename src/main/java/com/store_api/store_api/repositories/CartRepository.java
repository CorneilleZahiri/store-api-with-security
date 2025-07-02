package com.store_api.store_api.repositories;

import com.store_api.store_api.entities.Cart;
import com.store_api.store_api.entities.CartItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    @EntityGraph(attributePaths = {"product", "cart"})
    @Query("select c from CartItem c where c.cart.id = :idCart and c.product.id = :idProduct")
    Optional<CartItem> getCartItem(@Param("idCart") UUID idCart, @Param("idProduct") Long idProduct);
}
