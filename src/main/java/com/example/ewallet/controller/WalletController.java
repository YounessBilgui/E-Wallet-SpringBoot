package com.example.ewallet.controller;


import com.example.ewallet.entities.Wallet;
import com.example.ewallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService){
        this.walletService = walletService;
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

    @PostMapping("user/{userId}")
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


}
