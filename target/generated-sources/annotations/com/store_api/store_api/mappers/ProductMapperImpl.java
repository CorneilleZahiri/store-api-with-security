package com.store_api.store_api.mappers;

import com.store_api.store_api.dtos.CartProductsDto;
import com.store_api.store_api.dtos.ProductDto;
import com.store_api.store_api.dtos.RegisterProductRequest;
import com.store_api.store_api.entities.Category;
import com.store_api.store_api.entities.Product;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-02T12:31:51+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto productToDto(Product product) {
        if ( product == null ) {
            return null;
        }

        Byte categoryId = null;
        Long id = null;
        String name = null;
        String description = null;
        BigDecimal price = null;

        categoryId = productCategoryId( product );
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();

        ProductDto productDto = new ProductDto( id, name, description, price, categoryId );

        return productDto;
    }

    @Override
    public CartProductsDto cartProductToDto(Product product) {
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

    @Override
    public Product ProductDtoToEntity(RegisterProductRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( request.getName() );
        product.description( request.getDescription() );
        product.price( request.getPrice() );

        return product.build();
    }

    @Override
    public void update(RegisterProductRequest request, Product product) {
        if ( request == null ) {
            return;
        }

        product.setName( request.getName() );
        product.setDescription( request.getDescription() );
        product.setPrice( request.getPrice() );
    }

    private Byte productCategoryId(Product product) {
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getId();
    }
}
