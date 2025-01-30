package com.example.ewallet.controller;


import com.example.ewallet.dto.AccountSummaryDTO;
import com.example.ewallet.dto.RequestAccount;
import com.example.ewallet.dto.ResponseAccount;
import com.example.ewallet.entities.Account;
import com.example.ewallet.services.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getAccountByEmaail(@PathVariable String email){
        try{
            Account account = accountService.findByEmail(email);
            return ResponseEntity.ok(account);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Account With Email" + email + "Not Found");
        }
    }
    @GetMapping("/phone/{phone}")
    public ResponseEntity<?> getAccountByPhone(@PathVariable String phone){
        try{
            Account account = accountService.findByPhone(phone);
            return ResponseEntity.ok(account);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Account With Phone" + phone + "Not Found");
        }
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
    @GetMapping("/{id}/summary")
    public AccountSummaryDTO getAccountSummary(@PathVariable Long id){
        return accountService.getAccountSummary(id);
    }

    @GetMapping("/paginate")
    public ResponseEntity<Page<Account>> getAccounts(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortField, @RequestParam(defaultValue = "asc") String sortDirection
    ){
        Page<Account> accounts = accountService.getAccounts(page, size, sortField, sortDirection);


        return ResponseEntity.ok(accounts);
    }
    @GetMapping("/export/csv/{accountId}")
    public void exportSingleAccountToCSV(@PathVariable Long accountId, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        String filename = "account_" + accountId + ".csv";
        response.setHeader("Content-Disposition", "attachment; filename = " + filename );
        accountService.exportSingleAccountToCSV(accountId, response.getWriter());
    }

}
