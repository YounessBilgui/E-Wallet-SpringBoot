package com.example.ewallet.dto;

import lombok.Data;

@Data
public class TransferDTO {
    private Long fromWallet;
    private Long toWallet;
    private Double amount;
}
