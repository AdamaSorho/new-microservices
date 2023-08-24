package com.adamasorho.inventoryservice.services;

import com.adamasorho.inventoryservice.contrats.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCode);
}
