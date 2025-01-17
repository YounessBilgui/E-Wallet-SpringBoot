package com.example.ewallet.services;


import com.example.ewallet.entities.Transaction;
import com.example.ewallet.entities.Wallet;
import com.example.ewallet.repositories.TransactionRepository;
import com.example.ewallet.repositories.WalletRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository, WalletRepository walletRepository){
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }
    @Override
    public Transaction createTransaction(Transaction transaction) throws IllegalAccessException {
        Wallet wallet = walletRepository.findById(transaction.getWallet().getId())
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
        if(transaction.getType() == Transaction.TransactionType.CREDIT){
            wallet.setBalance(wallet.getBalance() + transaction.getAmount());
        } else if (transaction.getType() == Transaction.TransactionType.DEBIT) {
            if(wallet.getBalance() < transaction.getAmount()){
                throw new IllegalAccessException("Insufficient balance");
            }
            wallet.setBalance(wallet.getBalance() - transaction.getAmount());
        }
        walletRepository.save(wallet);

        transaction.setWallet(wallet);
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
    @Override
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }
}
