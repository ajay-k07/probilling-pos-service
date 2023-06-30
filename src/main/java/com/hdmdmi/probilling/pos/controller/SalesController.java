package com.hdmdmi.probilling.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hdmdmi.probilling.pos.request.InitialSalesRequest;
import com.hdmdmi.probilling.pos.request.SalesRequest;
import com.hdmdmi.probilling.pos.service.SalesService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SalesController {

    @Autowired
    SalesService salesService;

    @PostMapping("/sales")
    public ResponseEntity<Long> createNewSales(@RequestBody InitialSalesRequest salesRequest) {
        try {
            log.info("Creating a new Sales");
            Long salesId = salesService.createNewSales(salesRequest);
            log.info("New Sales Created with ID: {}", salesId);
            return ResponseEntity.status(HttpStatus.CREATED).body(salesId);
        } catch (Exception e) {
            log.error("Error While Creating New Sales", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }

    @PutMapping("/sales/{salesId}")
    public ResponseEntity<Void> completeTheSales(@RequestBody SalesRequest salesRequest, @PathVariable Long salesId) {
        try {
            log.info("Updating the sales to completed");
            salesService.completeTheSales(salesRequest, salesId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        } catch (Exception e) {
            log.error("Error while marking the sales as completed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
