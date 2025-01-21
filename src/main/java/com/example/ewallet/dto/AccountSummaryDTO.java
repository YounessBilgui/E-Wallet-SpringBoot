package com.example.ewallet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSummaryDTO {
    private String name;
    private String email;
    private String phone;
    private List<WalletDTO> wallets;
    private List<TransactionDTO> transaction;
}
