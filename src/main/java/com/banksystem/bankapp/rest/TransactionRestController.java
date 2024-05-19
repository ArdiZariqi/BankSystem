package com.banksystem.bankapp.rest;

import com.banksystem.bankapp.entities.Transaction;
import com.banksystem.bankapp.service.interfaces.ITransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    private ITransactionService iTransactionService;

    @Autowired
    public TransactionRestController(ITransactionService iTransactionService) {
        this.iTransactionService = iTransactionService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = iTransactionService.save(transaction);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Integer id) {
        Transaction transaction = iTransactionService.getById(id);
        if (transaction == null) {
            throw new EntityNotFoundException("Transaction not found with id " + id);
        }
        return ResponseEntity.ok(transaction);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = iTransactionService.getAll();
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Integer id) {
        iTransactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
