package com.example.ewallet.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setWallet(Wallet wallet) {
//        this.wallet = wallet;
//    }
}
