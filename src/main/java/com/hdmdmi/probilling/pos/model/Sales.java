package com.hdmdmi.probilling.pos.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.TenantId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean completed;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    private String customerPhoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sales")
    private List<SaleItem> saleItems;

    private BigDecimal totalAmount;

    private BigDecimal amountPaid;

    private String salesUserId;

    private String paymentType;

    @TenantId
    private String tenantId;

}
