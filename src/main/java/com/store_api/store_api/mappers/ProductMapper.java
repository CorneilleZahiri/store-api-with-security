package com.store_api.store_api.mappers;

import com.store_api.store_api.dtos.CartProductsDto;
import com.store_api.store_api.dtos.ProductDto;
import com.store_api.store_api.dtos.RegisterProductRequest;
import com.store_api.store_api.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto productToDto(Product product);

    CartProductsDto cartProductToDto(Product product);

    Product ProductDtoToEntity(RegisterProductRequest request);

    void update(RegisterProductRequest request, @MappingTarget Product product);
}
