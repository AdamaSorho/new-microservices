package com.adamasorho.productservice.controllers;

import com.adamasorho.productservice.contrats.ProductRequest;
import com.adamasorho.productservice.contrats.ProductResponse;
import com.adamasorho.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ProductRequest productRequest) {
        productService.create(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findAll() {
        // todo: add pagination

        return productService.findAll();
    }
}
