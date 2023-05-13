package com.vault.fundsloaderapplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OPERATIONS")
@Builder
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "UUID")
    private UUID uuid;

    @Column(name = "ID")
    private long id;

    @Column(name = "CUSTOMER_ID")
    private long customerId;

    @Column(name = "LOAD_AMOUNT")
    private BigDecimal loadAmount;

    @Column(name = "TIME")
    private LocalDateTime time;

    @Column(name = "ACCEPTED")
    private boolean accepted;
}