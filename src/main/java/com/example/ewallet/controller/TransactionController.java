package com.example.ewallet.controller;


import com.example.ewallet.entities.Transaction;
import com.example.ewallet.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws IllegalAccessException {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(createdTransaction);
    }
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransaction(){
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<List<Transaction>> getTransactionsByWalletId(@PathVariable Long walletId){
        List<Transaction> transactions = transactionService.getTransactionsByWalletId(walletId);
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/wallet/{walletId}/status/{status}")
    public ResponseEntity<List<Transaction>> getTransactionsByWalletIdAndStatus(@PathVariable Long walletId, @PathVariable Transaction.TransactionStatus status ){
        List<Transaction> transactions = transactionService.getTransactionsByWalletIdAndStatus(walletId, status);
        return ResponseEntity.ok(transactions);
    }
    @GetMapping("/wallet/{walletId}/type/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByWalletIdAndType(@PathVariable Long walletId, @PathVariable Transaction.TransactionType type)
    {
        List<Transaction> transactions = transactionService.getTransactionsByWalletIdAndType(walletId, type);
        return ResponseEntity.ok(transactions);
    }

}
