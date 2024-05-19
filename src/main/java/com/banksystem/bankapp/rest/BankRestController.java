package com.banksystem.bankapp.rest;

import com.banksystem.bankapp.entities.Bank;
import com.banksystem.bankapp.service.interfaces.IBankService;
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

    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody Bank Bank) {
        Bank createdBank = iBankService.save(Bank);
        return ResponseEntity.ok(createdBank);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable Integer id) {
        Bank Bank = iBankService.getById(id);
        return Bank != null ? ResponseEntity.ok(Bank) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Bank>> getAllBanks() {
        List<Bank> Banks = iBankService.getAll();
        return ResponseEntity.ok(Banks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Integer id) {
        iBankService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
