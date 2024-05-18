package com.banksystem.bankapp.service.interfaces;

import com.banksystem.bankapp.entities.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionService {
    Transaction save(Transaction transaction);
    Transaction getById(Integer id);
    List<Transaction> getAll();
    void delete(Integer id);
    List<Transaction> getTransactionsByAccountId(Integer accountId);
    public BigDecimal calculateFee(Transaction transaction);
}
