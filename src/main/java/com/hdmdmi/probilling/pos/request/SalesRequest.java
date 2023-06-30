package com.hdmdmi.probilling.pos.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.hdmdmi.probilling.pos.model.SaleItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesRequest {
    private Long id;

    private boolean completed;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    private String customerPhoneNumber;

    private List<SaleItemRequest> saleItemsRequests;

    private BigDecimal totalAmount;

    private BigDecimal amountPaid;

    private String salesUserId;

    private String paymentType;
}
