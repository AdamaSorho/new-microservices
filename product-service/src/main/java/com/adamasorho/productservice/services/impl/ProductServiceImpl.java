package com.adamasorho.productservice.services.impl;

import com.adamasorho.productservice.contrats.ProductRequest;
import com.adamasorho.productservice.contrats.ProductResponse;
import com.adamasorho.productservice.models.Product;
import com.adamasorho.productservice.repositories.ProductRepository;
import com.adamasorho.productservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void create(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product {} saved", product.getId());
    }

    @Override
    public List<ProductResponse> findAll() {
        // Todo: add pagination
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapProductToProductResponse).toList();
    }

    private ProductResponse mapProductToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
