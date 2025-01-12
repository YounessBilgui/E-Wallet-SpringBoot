//package com.example.ewallet.services;
//
//
//import com.example.ewallet.entities.User;
//import com.example.ewallet.entities.Wallet;
//import com.example.ewallet.repositories.UserRepository;
//import com.example.ewallet.repositories.WalletRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class WalletServiceImpl implements WalletService
//{
//    private final WalletRepository walletRepository;
//    private final UserRepository userRepository;
//
//    @Autowired
//    public WalletServiceImpl (WalletRepository walletRepository, UserRepository userRepository){
//        this.walletRepository = walletRepository;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public Optional<Wallet> getWalletById(Long walletId){
//        return walletRepository.findById(walletId);
//
//    }
//
//    @Override
//    public Wallet createWallet(Long userId){
//        Optional<User> user = userRepository.findById(userId);
//        if(user.isPresent()){
//            Wallet wallet = new Wallet();
//            wallet.setUser(user.get());
//            return walletRepository.save(wallet);
//        }
//        else {
//            throw new RuntimeException("User not Found with ID" + userId);
//        }
//    }
//
//
//}
