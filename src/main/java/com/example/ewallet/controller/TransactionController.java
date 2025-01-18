package com.example.ewallet.controller;


import com.example.ewallet.entities.Transaction;
import com.example.ewallet.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id){
        try {
            Transaction transaction = transactionService.getTransactionById(id);
            return ResponseEntity.ok(transaction);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Transaction with ID" + id + "not found.");
        }
    }
    @GetMapping("/wallet/{walletId}/type/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByWalletIdAndType(@PathVariable Long walletId, @PathVariable Transaction.TransactionType type)
    {
        List<Transaction> transactions = transactionService.getTransactionsByWalletIdAndType(walletId, type);
        return ResponseEntity.ok(transactions);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<Transaction> updatedTransaction(@PathVariable Long id, @RequestBody Transaction updatedTransaction){
        try{
            Transaction transaction = transactionService.updatedTransaction(id, updatedTransaction);
            return ResponseEntity.ok(transaction);
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }
}
