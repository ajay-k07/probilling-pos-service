package com.hdmdmi.probilling.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hdmdmi.probilling.pos.model.Sales;

public interface SalesRepo extends JpaRepository<Sales, Long> {

}
