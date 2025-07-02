package com.store_api.store_api.controllers;

import com.store_api.store_api.dtos.ProductDto;
import com.store_api.store_api.dtos.RegisterProductRequest;
import com.store_api.store_api.entities.Category;
import com.store_api.store_api.entities.Product;
import com.store_api.store_api.mappers.ProductMapper;
import com.store_api.store_api.repositories.CategoryRepository;
import com.store_api.store_api.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<ProductDto> getAllProduct(
            @RequestHeader(required = false, name = "x-api-key") String keyApi,
            @RequestParam(required = false, name = "categoryId") Byte categoryId) {

        System.out.println(keyApi);

        List<Product> products;

        if (categoryId != null) {
            products = productRepository.findAllByCategoryId(categoryId);
        } else {
            products = productRepository.findAllWithCategory();
        }

        return products.stream().map(productMapper::productToDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productMapper.productToDto(product));
    }

    //Create Product
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody RegisterProductRequest request,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        //Récupérer la catégorie
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            ResponseEntity.notFound().build();
        }

        Product product = productMapper.ProductDtoToEntity(request);

        product.setCategory(category);
        Product saveProduct = productRepository.save(product);

        //Convertir le produit enregistré en Dto
        ProductDto productDto = productMapper.productToDto(saveProduct);
        //Créer le lien du produit crée
        URI location = uriComponentsBuilder
                .path("/products/{id}")
                .buildAndExpand(productDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(name = "id") Long id,
                                                    @RequestBody RegisterProductRequest productRequest) {
        //Récupérer la catégorie
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElse(null);
        if (category == null) {
            ResponseEntity.notFound().build();
        }

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        productMapper.update(productRequest, product);
        product.setCategory(category);

        productRepository.save(product);

        return ResponseEntity.ok(productMapper.productToDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable(name = "id") Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        productRepository.delete(product);

        return ResponseEntity.noContent().build();
    }
}
