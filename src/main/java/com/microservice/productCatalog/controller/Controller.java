package com.microservice.productCatalog.controller;

import com.microservice.productCatalog.dto.ProductRequest;
import com.microservice.productCatalog.dto.ProductResponse;
import com.microservice.productCatalog.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class Controller {

    private final Service service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        service.createProduct(productRequest);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> productResponseList(){
       return service.getAllProducts();
    }

}
