package com.example.ewallet.dto;



import lombok.Data;


import java.util.List;
import java.util.Map;

@Data
public class AccountSummaryDTO {
    private String name;
    private String email;
    private String phone;
    private Long walletId;
    private Double walletBalance;
    private List<Map<String, Object>> transaction;

    public AccountSummaryDTO(String email, String name, String phone, Long walletId, Double walletBalance, List<Map<String, Object>> transaction) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.walletId = walletId;
        this.walletBalance = walletBalance;
        this.transaction = transaction;
    }
}
