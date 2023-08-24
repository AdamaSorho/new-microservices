package com.adamasorho.productservice.services;

import com.adamasorho.productservice.contrats.ProductRequest;
import com.adamasorho.productservice.contrats.ProductResponse;

import java.util.List;

public interface ProductService {

    void create(ProductRequest productRequest);

    List<ProductResponse> findAll();
}
