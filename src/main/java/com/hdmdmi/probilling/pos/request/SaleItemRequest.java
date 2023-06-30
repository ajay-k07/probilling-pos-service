package com.hdmdmi.probilling.pos.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SaleItemRequest {

    private Long productId;

    private Long priceId;
    // This will make sure we reduce the count from the inventory
    private Long inventoryId;

    private int quantity;

    private double price;

}
