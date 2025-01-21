package com.example.ewallet.services;

import com.example.ewallet.dto.TransferDTO;
import com.example.ewallet.entities.Transaction;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction) throws IllegalAccessException;
    Transaction getTransactionById(Long id);
    List<Transaction> getTransactionsByWalletId(Long walletId);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByWalletIdAndStatus(Long walletId, Transaction.TransactionStatus status);
    List<Transaction> getTransactionsByWalletIdAndType(Long walletId, Transaction.TransactionType type);
    void deleteTransactionById(Long id);
    Transaction updatedTransaction(Long id, Transaction updatedTransaction);
    void transferFunds(TransferDTO transferDTO);
    List<Object[]> findByAccountId(Long accountId);
}
