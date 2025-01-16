package com.example.ewallet.repositories;

import com.example.ewallet.entities.Transaction;
import com.example.ewallet.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletId(Long walletId);
    List<Transaction> findByWalletIdAndStatus(Long walletId, Transaction.TransactionStatus status);
    List<Transaction> findByWalletIdAndType(Long walletId, Transaction.TransactionType type);
}
