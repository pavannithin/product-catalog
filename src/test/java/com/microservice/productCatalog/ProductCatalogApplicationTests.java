package com.microservice.productCatalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.productCatalog.dto.ProductRequest;
import com.microservice.productCatalog.repos.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductCatalogApplicationTests {

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;


    @Test
    void shouldCreateProduct() throws Exception {

        ProductRequest productRequest = getProductRequest();

        String stringResponse = objectMapper.writeValueAsString(productRequest);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/product").contentType(MediaType.APPLICATION_JSON)
                .content(stringResponse)).andExpect(status().isCreated());
        Assertions.assertTrue( productRepository.findAll().size() > 0);
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Product 1")
                .description("Product 1 description")
                .price("100")
                .build();
    }

}
