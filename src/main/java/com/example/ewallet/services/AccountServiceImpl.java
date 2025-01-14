package com.example.ewallet.services;


import com.example.ewallet.dto.RequestAccount;
import com.example.ewallet.dto.ResponseAccount;
import com.example.ewallet.entities.Account;
import com.example.ewallet.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllUsers() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getUserById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public ResponseAccount createUser(RequestAccount requestAccount){

        Account account = new Account();
        account.setName(requestAccount.getName());
        account.setEmail(requestAccount.getEmail());
        account.setPhone(requestAccount.getPhone());
        account.setPassword(requestAccount.getPassword());
        Account createdAccount = accountRepository.save(account);
        ResponseAccount responseAccount = new ResponseAccount();
        responseAccount.setName(createdAccount.getName());
        responseAccount.setEmail(createdAccount.getEmail());
        responseAccount.setPhone(createdAccount.getPhone());
        return responseAccount;

    }

    @Override
    public Account updateUser(Long id, Account account) {

        Optional<Account> existingUser = accountRepository.findById(id);
        if(existingUser.isPresent()){
            Account updateAccount = existingUser.get();
            updateAccount.setName(account.getName());
            updateAccount.setEmail(account.getEmail());
            updateAccount.setPhone(account.getPhone());
            updateAccount.setPassword(account.getPassword());
            return accountRepository.save(updateAccount);
        }
        throw new RuntimeException("User not Found With ID" + id);
    }

    @Override
    public void deleteUser(Long id) {

        //Optional<User> existingUser = userRepository.findById(id);
        if (accountRepository.existsById(id)){
            accountRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not Found With Id : " + id);
        }

    }

}
