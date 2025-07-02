package com.store_api.store_api.repositories;

import com.store_api.store_api.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}