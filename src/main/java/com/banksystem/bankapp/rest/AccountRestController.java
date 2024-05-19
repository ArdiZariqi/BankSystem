package com.banksystem.bankapp.rest;

import com.banksystem.bankapp.entities.Account;
import com.banksystem.bankapp.service.interfaces.IAccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = iAccountService.save(account);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccountById(@PathVariable Integer id) {
        Account account = iAccountService.getById(id);
        if (account == null) {
            throw new EntityNotFoundException("Account not found with id " + id);
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = iAccountService.getAll();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {
        iAccountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}