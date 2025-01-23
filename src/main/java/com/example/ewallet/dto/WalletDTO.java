package com.example.ewallet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class WalletDTO {
    private Long id;
    private Double balance;
}
