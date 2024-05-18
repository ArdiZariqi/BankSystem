package com.banksystem.bankapp.service.implementations;

import com.banksystem.bankapp.daos.AccountRepository;
import com.banksystem.bankapp.entities.Account;
import com.banksystem.bankapp.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService implements IAccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account getById(Integer id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account withdraw(Integer accountId, BigDecimal amount) {
        Account account = getById(accountId);
        if (account != null) {
            account.withdrawMoney(amount);
            return accountRepository.save(account);
        } else {
            throw new IllegalArgumentException("Account not found.");
        }
    }

    @Override
    public Account depositMoney(Integer accountId, BigDecimal amount) {
        Account account = getById(accountId);
        if (account != null) {
            account.deposit(amount);
            return accountRepository.save(account);
        } else {
            throw new IllegalArgumentException("Account not found.");
        }
    }
}