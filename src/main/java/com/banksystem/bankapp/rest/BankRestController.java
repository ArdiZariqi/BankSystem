package com.banksystem.bankapp.rest;

import com.banksystem.bankapp.entities.Bank;
import com.banksystem.bankapp.service.interfaces.IBankService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
public class BankRestController {

    private IBankService iBankService;

    @Autowired
    public BankRestController(IBankService iBankService) {
        this.iBankService = iBankService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        Bank createdBank = iBankService.save(bank);
        return ResponseEntity.ok(createdBank);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Bank> getBankById(@PathVariable Integer id) {
        Bank bank = iBankService.getById(id);
        if (bank == null) {
            throw new EntityNotFoundException("Bank not found with id " + id);
        }
        return ResponseEntity.ok(bank);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Bank>> getAllBanks() {
        List<Bank> banks = iBankService.getAll();
        return ResponseEntity.ok(banks);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteBank(@PathVariable Integer id) {
        iBankService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
