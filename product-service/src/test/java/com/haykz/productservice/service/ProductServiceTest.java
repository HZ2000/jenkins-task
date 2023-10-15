package com.haykz.productservice.service;

import com.haykz.productservice.dto.ProductRequest;
import com.haykz.productservice.model.Product;
import com.haykz.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductRequest productRequest = new ProductRequest("Test Product", "Test Description", new BigDecimal(10));
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        productService.createProduct(productRequest);

        // You can add assertions or verify interactions as needed
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any());
    }
}

