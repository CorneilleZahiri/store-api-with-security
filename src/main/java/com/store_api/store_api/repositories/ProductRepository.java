package com.store_api.store_api.repositories;


import com.store_api.store_api.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "category")
    List<Product> findAllByCategoryId(Byte categoryId);

    @EntityGraph(attributePaths = "category")
    @Query("select p from Product p")
    List<Product> findAllWithCategory();
}