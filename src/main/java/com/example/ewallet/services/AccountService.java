package com.example.ewallet.services;

import com.example.ewallet.dto.AccountSummaryDTO;
import com.example.ewallet.dto.RequestAccount;
import com.example.ewallet.dto.ResponseAccount;
import com.example.ewallet.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> getAllUsers();
    Optional<Account> getUserById(Long id);
    Account findByEmail(String email);
    Account findByPhone(String phone);
    ResponseAccount createUser(RequestAccount requestAccount);
    Account updateUser(Long id, Account account);
    void deleteUser(Long id);
    AccountSummaryDTO getAccountSummary(Long accountId);


    Page<Account> getAccounts(Integer page, Integer size, String sortField, String sortDirection);
}
