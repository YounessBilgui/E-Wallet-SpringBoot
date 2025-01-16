package com.example.ewallet.services;


import com.example.ewallet.entities.Transaction;
import com.example.ewallet.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }
    @Override
    public Transaction createTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }
    @Override
    public List<Transaction>getTransactionsByWalletId(Long walletId){
        return transactionRepository.findByWalletId(walletId);
    }
    @Override
    public List<Transaction>getTransactionsByWalletIdAndStatus(Long walletId, Transaction.TransactionStatus status){
        return transactionRepository.findByWalletIdAndStatus(walletId, status);
    }
    @Override
    public List<Transaction>getTransactionsByWalletIdAndType(Long walletId, Transaction.TransactionType type){
        return transactionRepository.findByWalletIdAndType(walletId, type);
    }
}
