package com.example.ewallet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceSummaryDTO {
    private Long walletId;
    private Double balance;

}
