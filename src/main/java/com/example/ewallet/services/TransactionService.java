package com.example.ewallet.services;

import com.example.ewallet.entities.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    List<Transaction> getTransactionsByWalletId(Long walletId);
    List<Transaction> getTransactionsByWalletIdAndStatus(Long walletId, Transaction.TransactionStatus status);
    List<Transaction> getTransactionsByWalletIdAndType(Long walletId, Transaction.TransactionType type);
}
