package com.banksystem.bankapp.service.interfaces;

import com.banksystem.bankapp.entities.Account;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountService {
    Account save(Account account);
    Account getById(Integer id);
    List<Account> getAll();
    void delete(Integer id);
    Account withdraw(Integer accountId, BigDecimal amount);
    Account depositMoney(Integer accountId, BigDecimal amount);
}
