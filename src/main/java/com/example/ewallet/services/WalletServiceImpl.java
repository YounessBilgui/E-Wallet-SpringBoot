package com.example.ewallet.services;


import com.example.ewallet.dto.BalanceSummaryDTO;
import com.example.ewallet.entities.Account;
import com.example.ewallet.entities.Wallet;
import com.example.ewallet.repositories.AccountRepository;
import com.example.ewallet.repositories.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService
{
    private final WalletRepository walletRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public WalletServiceImpl (WalletRepository walletRepository, AccountRepository accountRepository){
        this.walletRepository = walletRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Wallet> getWalletById(Long walletId){
        return walletRepository.findById(walletId);

   }
   @Override
   public List<Wallet> getAllWallets(){
        return walletRepository.findAll();
   }
    @Override
    public Wallet createWallet(Long userId){
        Optional<Account> account = accountRepository.findById(userId);
        if(account.isPresent()){
            Wallet wallet = new Wallet();
            wallet.setAccount(account.get());
            return walletRepository.save(wallet);
        }
        else {
            throw new RuntimeException("User not Found with ID" + userId);
        }
    }

    @Override
    public Wallet updateBalance(Long walledId, Double amount) {
        Optional<Wallet> wallet = walletRepository.findById(walledId);
        if (wallet.isPresent()) {
            Wallet existingWallet = wallet.get();
            existingWallet.setBalance(existingWallet.getBalance() + amount);
            return walletRepository.save(existingWallet);
        }
        else {
            throw new RuntimeException("Wallet not found with ID " + walledId);
        }
    }

    @Override
    public void deleteWallet(Long walletId) {
        if (walletRepository.existsById(walletId)) {
            walletRepository.deleteById(walletId);
        } else {
            throw new RuntimeException("Wallet not found with ID: " + walletId);
        }
    }

    @Override
    public Wallet getWalletByAccountId(Long accountId) throws IllegalAccessException {
        Wallet wallet = walletRepository.findByAccountId(accountId);
        if (wallet == null){
            throw new IllegalAccessException("Wallet not found for account ID" + accountId);
        }
        return wallet;
    }
    @Override
    public BalanceSummaryDTO getBalanceSummary(Long accountId){

        Wallet wallet = walletRepository.findByAccountId(accountId);

       if(wallet == null){
           throw  new EntityNotFoundException("wallet not found for accountID" + accountId);
       }

        return new BalanceSummaryDTO(wallet.getId(), wallet.getBalance());
    }

    @Override
    public Page<Wallet> getWallets(Integer page, Integer size, String sortField, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Wallet> wallets = walletRepository.findAll(pageable);
        return wallets;
    }

}
