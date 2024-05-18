package com.banksystem.bankapp.service.implementations;

import com.banksystem.bankapp.daos.BankRepository;
import com.banksystem.bankapp.entities.Bank;
import com.banksystem.bankapp.service.interfaces.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService implements IBankService {

    private BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    public Bank getById(Integer id) {
        Optional<Bank> result = bankRepository.findById(id);
        Bank theBank = null;
        if (result.isPresent()) {
            theBank = result.get();
        } else {
            throw new RuntimeException("Bank with ID " + id + " not found");
        }
        return theBank;
    }

    @Override
    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        bankRepository.deleteById(id);
    }
}