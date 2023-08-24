package com.adamasorho.inventoryservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String skuCode;
    private Integer quantity;
}
