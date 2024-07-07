package com.microservice.productCatalog.service;

import com.microservice.productCatalog.dto.ProductRequest;
import com.microservice.productCatalog.dto.ProductResponse;
import com.microservice.productCatalog.model.Product;
import com.microservice.productCatalog.repos.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = new Product().builder()
                .description(productRequest.getDescription()).name(productRequest.getName()).price(productRequest.getPrice()).build();
        productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> response = productRepository.findAll();
        return response.stream().map(this::mapResponseTOResponseObject).toList();
    }

    private ProductResponse mapResponseTOResponseObject(Product p) {
        return new ProductResponse().builder()
                .description(p.getDescription()).id(p.getId()).name(p.getName()).price(p.getPrice()).build();
    }
}
