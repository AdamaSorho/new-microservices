package com.adamasorho.inventoryservice.repositories;

import com.adamasorho.inventoryservice.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("select i from Inventory i where i.skuCode = :skuCode and i.quantity > 0")
    Optional<Inventory> findBySkuCode(String skuCode);

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
