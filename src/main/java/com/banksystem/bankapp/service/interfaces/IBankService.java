package com.banksystem.bankapp.service.interfaces;

import com.banksystem.bankapp.entities.Bank;

import java.util.List;

public interface IBankService {
    Bank save(Bank bank);
    Bank getById(Integer id);
    List<Bank> getAll();
    void delete(Integer id);
}
