package com.adamasorho.orderservice.services;

import com.adamasorho.orderservice.contrats.OrderRequest;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest);
}
