package com.example.ewallet.repositories;

import com.example.ewallet.entities.Transaction;
import com.example.ewallet.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
