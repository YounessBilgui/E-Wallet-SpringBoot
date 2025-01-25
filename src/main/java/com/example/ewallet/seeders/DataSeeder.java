package com.example.ewallet.seeders;

import com.example.ewallet.entities.Account;
import com.example.ewallet.entities.Wallet;
import com.example.ewallet.repositories.AccountRepository;
import com.example.ewallet.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



@Component
//@ConditionalOnProperty(name = "app.seed-data", havingValue = "true", matchIfMissing = false )
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    public final Faker fake = new Faker();
    public final Random random = new Random();
    public final AccountRepository accountRepository;
    public final WalletRepository walletRepository;



    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++){
            Account account = new Account();
            account.setName(fake.name().name());
            account.setEmail(fake.name().username()+"@example.com");
            account.setPhone(fake.phoneNumber().phoneNumber());
            account.setPassword(fake.internet().password(10, 20, true ,true));

//             wallet seeders
//            List<Wallet> wallets = new ArrayList<>();
//            int walletCount = fake.number().numberBetween(2, 4);
//            for (int j = 0; j < walletCount; j++){
//                Wallet wallet = new Wallet();
//                wallet.setBalance(fake.number().randomDouble(2, 100, 10000));
//                wallet.setAccount(account);
//                wallets.add(wallet);
//            }
//
//            account.setWallets(wallets);
            accountRepository.save(account);
        }
    }
}
