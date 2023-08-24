package com.adamasorho.orderservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItems {
    @Id
    @GeneratedValue
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
