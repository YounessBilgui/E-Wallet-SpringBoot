package com.example.ewallet.controller;


import com.example.ewallet.dto.TransferDTO;
import com.example.ewallet.entities.Account;
import com.example.ewallet.entities.Transaction;
import com.example.ewallet.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        if ((transaction.getFromWallet() == null) || (transaction.getToWallet() == null) || (transaction.getAmount() == null)) {
            return ResponseEntity.badRequest().body("Invalid transaction data. Please provide all required fields.");
        }

        try {
            Transaction createdTransaction = transactionService.createTransaction(transaction);
            return ResponseEntity.ok(createdTransaction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
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
    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteTransactionById(@PathVariable Long id){
        transactionService.deleteTransactionById(id);
        return ResponseEntity.ok("transaction Deleted Successfully");
    }
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransferDTO transferDTO) {
        try {
            transactionService.transferFunds(transferDTO);
            return ResponseEntity.ok("Transfer successful!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the transfer.");
        }
    }
    @GetMapping("/account/{accountId}")
    public ResponseEntity<Map<String, Object>> findByAccountId(@PathVariable Long accountId){
        Map<String, Object> response = transactionService.findByAccountId(accountId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/paginate")
    public ResponseEntity<Page<Transaction>> getAccounts(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortField, @RequestParam(defaultValue = "asc") String sortDirection
    ){
        Page<Transaction> transactions = transactionService.getTransaction(page, size, sortField, sortDirection);


        return ResponseEntity.ok(transactions);
    }
}
