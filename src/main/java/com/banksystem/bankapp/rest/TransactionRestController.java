package com.banksystem.bankapp.rest;

import com.banksystem.bankapp.entities.Transaction;
import com.banksystem.bankapp.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {
    @Autowired
    private ITransactionService iTransactionService;

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return iTransactionService.save(transaction);
    }

    @GetMapping("/{id}")
    public Transaction getById(@PathVariable Integer id) {
        Transaction transaction = iTransactionService.getById(id);
        if(transaction == null){
            throw new RuntimeException("Transaction not found for id: " + id);
        }
        return transaction;
    }

    @GetMapping
    public List<Transaction> getAll() {
        return iTransactionService.getAll();
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Integer id) {
        Transaction transaction = iTransactionService.getById(id);
        if(transaction == null){
            throw new RuntimeException("Transaction not found for id: " + id);
        }
        iTransactionService.delete(id);
        return "Deleted transaction with id " + id;
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactionsByAccountId(@PathVariable Integer accountId) {
        List<Transaction> transactions = iTransactionService.getTransactionsByAccountId(accountId);
        if(transactions == null){
            throw new RuntimeException("Transaction not found for account " + accountId);
        }
        return transactions;
    }
}