package com.example.ewallet.services;

import com.example.ewallet.entities.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> getAllUsers();
    Optional<Account> getUserById(Long id);
    Account createUser(Account account);
    Account updateUser(Long id, Account account);
    void deleteUser(Long id);

}
