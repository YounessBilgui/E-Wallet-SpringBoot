package com.example.ewallet.controller;


import com.example.ewallet.dto.RequestAccount;
import com.example.ewallet.dto.ResponseAccount;
import com.example.ewallet.entities.Account;
import com.example.ewallet.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllUsers(){
        List<Account> account = accountService.getAllUsers();
        return ResponseEntity.ok(account);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Account>> getUserById(@PathVariable Long id){
        Optional<Account> user = accountService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @PostMapping
    public ResponseEntity<ResponseAccount> createUser(@RequestBody RequestAccount requestAccount){
        ResponseAccount createdAccount = accountService.createUser(requestAccount);
        return ResponseEntity.ok(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateUser(@PathVariable Long id, @RequestBody Account account){
        Account updatedAccount = accountService.updateUser(id, account);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteUser(@PathVariable Long id){
        accountService.deleteUser(id);
        return ResponseEntity.ok("User Deleted Successfully");
    }

}
