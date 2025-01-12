//package com.example.ewallet.entities;
//
//
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "wallets")
//@Data
//@Builder
//
//public class Wallet {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // RELATION SQL IN JPA SPRING BOOT
//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private Account account;
//    //precision = 15: This defines the total number of digits that can be stored
//    //scale = 2: This specifies how many decimal places are allowed after the decimal point
//    @Column(nullable = false)
//    private Double balance = 0.00;
//
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt = LocalDateTime.now();
//
//    @PreUpdate
//    public void setUpdatedAt() {
//        this.updatedAt = LocalDateTime.now();
//    }
//    public Wallet(long id, Account account, double balance){
//        this.id= id;
//        this.account = account;
//        this.balance = balance;
//    }
//
//}
