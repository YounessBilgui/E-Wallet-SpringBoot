package com.example.ewallet.services;

import com.example.ewallet.dto.BalanceSummaryDTO;
import com.example.ewallet.entities.Account;
import com.example.ewallet.entities.Wallet;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface WalletService {
    Wallet createWallet(Long userId);
    Optional<Wallet> getWalletById(Long walletId);
    List<Wallet> getAllWallets();
    Wallet updateBalance(Long walledId, Double amount);
    void deleteWallet(Long walletId);
    Wallet getWalletByAccountId(Long userId) throws IllegalAccessException;

    BalanceSummaryDTO getBalanceSummary(Long userId) throws IllegalAccessException;
    Page<Wallet> getWallets(Integer page, Integer size, String sortField, String sortDirection);
}
