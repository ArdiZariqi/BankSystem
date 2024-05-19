package com.banksystem.bankapp.rest;

import com.banksystem.bankapp.entities.Account;
import com.banksystem.bankapp.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    private IAccountService iAccountService;

    @Autowired
    public AccountRestController(IAccountService iAccountService) {
        this.iAccountService = iAccountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account Account) {
        Account createdAccount = iAccountService.save(Account);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Integer id) {
        Account Account = iAccountService.getById(id);
        return Account != null ? ResponseEntity.ok(Account) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> Accounts = iAccountService.getAll();
        return ResponseEntity.ok(Accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {
        iAccountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}