package com.example.ewallet.services;


import com.example.ewallet.dto.TransferDTO;
import com.example.ewallet.entities.Transaction;
import com.example.ewallet.entities.Wallet;
import com.example.ewallet.repositories.TransactionRepository;
import com.example.ewallet.repositories.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository, WalletRepository walletRepository){
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }
    @Override
    public Transaction createTransaction(Transaction transaction) {
        if (transaction.getFromWallet() == null || transaction.getToWallet() == null) {
            throw new IllegalArgumentException("Wallet information is missing in the transaction.");
        }

        Wallet fromWallet = walletRepository.findById(transaction.getFromWallet().getId())
                .orElseThrow(() -> new IllegalArgumentException("FromWallet does not exist"));

        Wallet toWallet = walletRepository.findById(transaction.getToWallet().getId())
                .orElseThrow(() -> new IllegalArgumentException("ToWallet does not exist"));

        if (fromWallet.getBalance() < transaction.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance in the sender wallet.");
        }

        // Update balances
        fromWallet.setBalance(fromWallet.getBalance() - transaction.getAmount());
        toWallet.setBalance(toWallet.getBalance() + transaction.getAmount());

        // Save wallets
        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        // Save transaction
        transaction.setStatus(Transaction.TransactionStatus.SUCCESS);
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
    @Override
    public Transaction getTransactionById(Long id){

        return transactionRepository.findById(id).orElse(null);
    }
    @Override
    public void deleteTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transaction Not Found"));
        transactionRepository.delete(transaction);
    }
    @Override
    public Transaction updatedTransaction(Long id, Transaction updatedTransaction){
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("Transaction not found"));

        existingTransaction.setType(updatedTransaction.getType());
        existingTransaction.setStatus(updatedTransaction.getStatus());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setDescription(updatedTransaction.getDescription());
        existingTransaction.setUpdatedAt(LocalDateTime.now());
        return transactionRepository.save(existingTransaction);
    }
    @Transactional
    @Override
    public void transferFunds(TransferDTO transferDTO) {
        Wallet fromWallet = walletRepository.findById(transferDTO.getFromWallet())
                .orElseThrow(() -> new IllegalArgumentException("Sender wallet not found."));
        Wallet toWallet = walletRepository.findById(transferDTO.getToWallet())
                .orElseThrow(() -> new IllegalArgumentException("Recipient wallet not found."));

        if (fromWallet.getBalance() < transferDTO.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance in sender's wallet.");
        }

        // Perform atomic updates
        fromWallet.setBalance(fromWallet.getBalance() - transferDTO.getAmount());
        toWallet.setBalance(toWallet.getBalance() + transferDTO.getAmount());

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        // Log the transaction
        Transaction transaction = new Transaction();
        transaction.setFromWallet(fromWallet);
        transaction.setToWallet(toWallet);
        transaction.setAmount(transferDTO.getAmount());
        transaction.setDescription("Funds Transfer");
        transactionRepository.save(transaction);
    }

    @Override
    public List<Object[]> findByAccountId(Long accountId) {
        List<Object[]> transactions = transactionRepository.findByAccountId(accountId);
        if (transactions.isEmpty()){
            throw new IllegalArgumentException("this transaction not associate with that" + accountId);
        }
        return transactions;
    }


}
