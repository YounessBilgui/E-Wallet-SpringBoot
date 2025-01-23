package com.example.ewallet.repositories;

import com.example.ewallet.entities.Transaction;
import com.example.ewallet.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletId(Long walletId);
    List<Transaction> findByWalletIdAndStatus(Long walletId, Transaction.TransactionStatus status);
    List<Transaction> findByWalletIdAndType(Long walletId, Transaction.TransactionType type);
//    @Query("SELECT t.fromWallet.id, t.toWallet.id, t.type, t.amount, t.status, t.description FROM Transaction t JOIN t.wallet w WHERE w.account.id = :accountId")
//    List<Object[]> findByAccountId(@Param("accountId") Long accountId);
    @Query("SELECT t.fromWallet.id, t.toWallet.id, t.type, t.amount, t.status, t.description " +
            "FROM Transaction t JOIN t.wallet w WHERE w.account.id = :accountId")
    List<Object[]> findByAccountId(@Param("accountId") Long accountId);
}
