package com.hdmdmi.probilling.pos.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdmdmi.probilling.pos.request.InitialSalesRequest;
import com.hdmdmi.probilling.pos.request.SaleItemRequest;
import com.hdmdmi.probilling.pos.request.SalesRequest;

import lombok.extern.slf4j.Slf4j;

import com.hdmdmi.probilling.pos.feign.InventoryProxyService;
import com.hdmdmi.probilling.pos.model.SaleItem;
import com.hdmdmi.probilling.pos.model.Sales;
import com.hdmdmi.probilling.pos.repository.SalesRepo;

@Service
@Slf4j
public class SalesService {

    @Autowired
    SalesRepo salesRepo;

    @Autowired
    InventoryProxyService inventoryProxyService;

    public Long createNewSales(InitialSalesRequest salesRequest) {
        try {
            log.info("Creating a new Sales");
            Sales sales = Sales.builder()
                    .salesUserId(salesRequest.getSalesUserId())
                    .customerPhoneNumber(salesRequest.getCustomerPhoneNumber())
                    .createdDate(LocalDate.now()).build();

            sales = salesRepo.save(sales);
            log.info("New Sales Created with ID: {}", sales.getId());
            return sales.getId();
        } catch (Exception e) {
            log.error("Error While Trying to create sales", e);
            throw e;
        }

    }

    public void completeTheSales(SalesRequest salesRequest, Long salesId) {

        log.info("Marking the sales as completed {}", salesRequest);

        Optional<Sales> optionalSales = salesRepo.findById(salesId);

        if (optionalSales.isPresent()) {

            Sales sales = optionalSales.get();

            sales.setUpdatedDate(LocalDate.now());
            sales.setCompleted(salesRequest.isCompleted());
            sales.setTotalAmount(salesRequest.getTotalAmount());
            sales.setAmountPaid(salesRequest.getAmountPaid());
            sales.setPaymentType(salesRequest.getPaymentType());
            List<SaleItem> saleItems = getSalesItemFromSalesRequest(sales, salesRequest);
            sales.setSaleItems(saleItems);
            log.info("Sales Object {}", sales);
            salesRepo.save(sales);

        } else {
            log.info("Sales ID: {} not found", salesRequest.getId());
        }

    }

    public List<SaleItem> getSalesItemFromSalesRequest(Sales sales, SalesRequest salesRequest) {
        List<SaleItem> saleItems = new ArrayList<>();
        for (SaleItemRequest salesItemRequest : salesRequest.getSaleItemsRequests()) {
            SaleItem saleItem = SaleItem
                    .builder()
                    .productId(salesItemRequest.getProductId())
                    .priceId(salesItemRequest.getPriceId())
                    .inventoryId(salesItemRequest.getInventoryId())
                    .sales(sales)
                    .quantity(salesItemRequest.getQuantity())
                    .price(salesItemRequest.getPrice())
                    .build();
            inventoryProxyService.decrementQuantity(salesItemRequest.getInventoryId(), salesItemRequest.getQuantity());
            saleItems.add(saleItem);
        }
        return saleItems;
    }

}
