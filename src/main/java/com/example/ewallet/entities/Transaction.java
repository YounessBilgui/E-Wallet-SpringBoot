//package com.example.ewallet.entities;
//
//
//import jakarta.persistence.*;
//
//import java.time.LocalDateTime;
//


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

//@Entity
//Table(name = "transactions")
//@Data
//@Builder
//public class Transaction {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "wallet_id", nullable = false)
//    private Wallet wallet;
//
//    @Column(nullable = false, length = 50)
//    @Enumerated(EnumType.STRING)
//    private Transaction type; // Credit or Debit
//
//    @Column(nullable = false, length = 50)
//    @Enumerated(EnumType.STRING)
//    private Transaction status; // SUCCESS or FAILED
//
//    @Column(nullable = false)
//    private Double amount;
//
//    private String description;
//
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    // CONSTRUCTOR
//
//    public Transaction(Long id, Wallet wallet, Transaction type, Transaction status, String description, LocalDateTime createdAt) {
//        this.id = id;
//        this.wallet = wallet;
//        this.type = type;
//        this.status = status;
//        this.description = description;
//        this.createdAt = createdAt;
//    }
//    public enum TransactionType{
//        CREDIT,
//        DEBIT
//    }
//    public enum TransactionStatus{
//        SUCCESS,
//        FAILED
//    }
//}
