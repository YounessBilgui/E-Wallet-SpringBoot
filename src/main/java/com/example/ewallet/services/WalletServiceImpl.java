package com.example.ewallet.services;


import com.example.ewallet.entities.Account;
import com.example.ewallet.entities.Wallet;
import com.example.ewallet.repositories.AccountRepository;
import com.example.ewallet.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Wallet getWalletByAccountId(Long accoundId) throws IllegalAccessException {
        Wallet wallet = walletRepository.findByAccountId(accoundId);
        if (wallet == null){
            throw new IllegalAccessException("Wallet not found for account ID" + accoundId);
        }
        return wallet;
    }


}
