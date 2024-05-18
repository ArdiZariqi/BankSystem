package com.banksystem.bankapp.rest;

import com.banksystem.bankapp.entities.Bank;
import com.banksystem.bankapp.service.interfaces.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Bank> addBank(@RequestBody Bank bank) {
        var savedBank = iBankService.save(bank);
        return new ResponseEntity<>(savedBank, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public Bank getById(@PathVariable Integer id) {
        Bank bank = iBankService.getById(id);
        if(bank == null){
            throw new RuntimeException("Bank not found for id: " + id);
        }
        return bank;
    }

    @GetMapping
    public List<Bank> getAll() {
        return iBankService.getAll();
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Integer id) {
        Bank bank = iBankService.getById(id);
        if(bank == null){
            throw new RuntimeException("Bank not found for id: " + id);
        }
        iBankService.delete(id);
        return "Deleted bank with id - " + id;
    }
}
