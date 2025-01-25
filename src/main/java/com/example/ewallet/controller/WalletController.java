package com.example.ewallet.controller;


import com.example.ewallet.dto.BalanceSummaryDTO;
import com.example.ewallet.entities.Wallet;
import com.example.ewallet.services.AccountService;
import com.example.ewallet.services.WalletService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;
    private final AccountService accountService;

    @Autowired
    public WalletController(WalletService walletService, AccountService accountService){
        this.walletService = walletService;
        this.accountService = accountService;
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<Optional<Wallet>> getWalletById(@PathVariable Long walletId){
        Optional<Wallet> wallet = walletService.getWalletById(walletId);
        return ResponseEntity.ok(wallet);
    }

    @GetMapping
    public ResponseEntity<List<Wallet>> getAllWallets(){
        List<Wallet> wallets = walletService.getAllWallets();
        return ResponseEntity.ok(wallets);
    }
    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getWalletByAccountId(@PathVariable Long accountId){
        try {
            Wallet wallet = walletService.getWalletByAccountId(accountId);
            return ResponseEntity.ok(wallet);
        }catch (IllegalArgumentException | IllegalAccessException e){
            return ResponseEntity.badRequest().body("Wallet not Found");
        }
    }

    @PostMapping("account/{userId}")
    public ResponseEntity<Wallet> createWallet(@PathVariable Long userId){
        Wallet wallet = walletService.createWallet(userId);
        return ResponseEntity.ok(wallet);
    }

    @PutMapping("/{walletId}")
    public ResponseEntity<Wallet> updateBalance(@PathVariable Long walletId, @RequestParam  Double amount){
        Wallet updateWallet = walletService.updateBalance(walletId, amount);
        return ResponseEntity.ok(updateWallet);
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<String> deleteWallet(@PathVariable Long walletId){
        walletService.deleteWallet(walletId);
        return ResponseEntity.ok("Wallet deleted successfully!");
    }
    @GetMapping("/balance-summary/{accountId}")
    public ResponseEntity<?> getBalanceSummary(@RequestParam Long accountId){
        try {
            BalanceSummaryDTO summary = walletService.getBalanceSummary(accountId);
            return ResponseEntity.ok(summary);
        } catch (EntityNotFoundException | IllegalAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }


}
