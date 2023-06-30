package com.hdmdmi.probilling.pos.model;

import org.hibernate.annotations.TenantId;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Sales Item doesnt represent the product instead it represent  
 * the inventory item which holds the count and the product id
 */

@Entity
@Table(name = "sale_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sales_id")
    private Sales sales;

    private Long productId;

    private Long priceId;

    private double price;

    // This will make sure we reduce the count from the inventory
    private Long inventoryId;

    private int quantity;

    @TenantId
    private String tenantId;

}
