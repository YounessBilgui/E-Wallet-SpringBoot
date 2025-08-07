package com.example.ewallet.services;


import com.example.ewallet.dto.TransferDTO;
import com.example.ewallet.entities.Account;
import com.example.ewallet.entities.Transaction;
import com.example.ewallet.entities.Wallet;
import com.example.ewallet.repositories.AccountRepository;
import com.example.ewallet.repositories.TransactionRepository;
import com.example.ewallet.repositories.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AccountRepository accountRepository;

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
    public Transaction deleteTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transaction Not Found"));
        transactionRepository.delete(transaction);
        return transaction;
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

        // Log the transaction for the sender (debit)
        Transaction debitTransaction = new Transaction();
        debitTransaction.setWallet(fromWallet);
        debitTransaction.setFromWallet(fromWallet);
        debitTransaction.setToWallet(toWallet);
        debitTransaction.setAmount(transferDTO.getAmount());
        debitTransaction.setType(Transaction.TransactionType.DEBIT);
        debitTransaction.setStatus(Transaction.TransactionStatus.SUCCESS);
        debitTransaction.setDescription("Funds Transfer");
        transactionRepository.save(debitTransaction);

        // Log the transaction for the receiver (credit)
        Transaction creditTransaction = new Transaction();
        creditTransaction.setWallet(toWallet);
        creditTransaction.setFromWallet(fromWallet);
        creditTransaction.setToWallet(toWallet);
        creditTransaction.setAmount(transferDTO.getAmount());
        creditTransaction.setType(Transaction.TransactionType.CREDIT);
        creditTransaction.setStatus(Transaction.TransactionStatus.SUCCESS);
        creditTransaction.setDescription("Funds Transfer");
        transactionRepository.save(creditTransaction);
    }

    @Override
    public Map<String, Object> findByAccountId(Long accountId) {
        List<Object[]> transactions = transactionRepository.findByAccountId(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow(()
                -> new NoSuchElementException("Account not found"));
        Wallet wallet = walletRepository.findByAccountId(accountId);
        if (wallet == null) throw new NoSuchElementException("Wallet not found for this account");


        Map<String, Object> response = new HashMap<>();
        assert account != null;
        response.put("accountName", account.getName());
        response.put("walletId", wallet.getId());

//        List<Map<String, Object>> transactionsRes = new ArrayList<>();
//        for (Object[] entry : transactions){
//            Map<String, Object> temp = new HashMap<>();
//            temp.put("fromWalletId", entry[0]);
//            temp.put("toWalletId", entry[1]);
//            temp.put("type", entry[2]);
//            temp.put("amount", entry[3]);
//            temp.put("status", entry[4]);
//            temp.put("description", entry[5]);
//            transactionsRes.add(temp);
//        }
//        response.put("transactions", transactionsRes);
//        return response;
//    }
        List<Map<String, Object>> transactionsRes = transactions.stream()
                .map(entry -> {
                    Map<String, Object> temp = new HashMap<>();
                    temp.put("fromWalletId", entry[0]);
                    temp.put("toWalletId", entry[1]);
                    temp.put("type", entry[2]);
                    temp.put("amount", entry[3]);
                    temp.put("status", entry[4]);
                    temp.put("description", entry[5]);
                    return temp;
                })
                .collect(Collectors.toList());

        response.put("transactions", transactionsRes);

        return response;
    }

    @Override
    public Page<Transaction> getTransaction(Integer page, Integer size, String sortField, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions;
    }

}
