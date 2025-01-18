package com.example.ewallet.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;



import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TransactionType type; // Credit or Debit

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status; // SUCCESS or FAILED

    @Column(nullable = false)
    private Double amount;

    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    // CONSTRUCTOR

    public enum TransactionType{
        CREDIT,
        DEBIT
    }
    public enum TransactionStatus{
        SUCCESS,
        FAILED
    }
}
