package com.banksystem.bankapp.rest;

import com.banksystem.bankapp.entities.Account;
import com.banksystem.bankapp.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public Account addAccount(@RequestBody Account account) {
        return iAccountService.save(account);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable Integer id) {
        Account account = iAccountService.getById(id);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Account> getAll() {
        return iAccountService.getAll();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        Account account = iAccountService.getById(id);
        if (account == null) {
            throw new RuntimeException("Account not found for id - " + id);
        }
        iAccountService.delete(id);
        return "Deleted Account with id - " + id;
    }

    @PostMapping("/withdraw")
    public Account withdraw(@RequestParam Integer accountId, @RequestParam BigDecimal amount) {
        Account account = iAccountService.getById(accountId);
        if (account == null) {
            throw new RuntimeException("Account not found for id - " + accountId);
        }
        return iAccountService.withdraw(accountId, amount);
    }

    @PostMapping("/deposit")
    public Account deposit(@RequestParam Integer accountId, @RequestParam BigDecimal amount) {
        return iAccountService.depositMoney(accountId, amount);
    }
}