package com.banksystem.bankapp.service.implementations;

import com.banksystem.bankapp.daos.TransactionRepository;
import com.banksystem.bankapp.entities.Account;
import com.banksystem.bankapp.entities.Bank;
import com.banksystem.bankapp.entities.Transaction;
import com.banksystem.bankapp.enums.TransactionFeeType;
import com.banksystem.bankapp.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {

        BigDecimal fee = calculateFee(transaction);
        transaction.setFee(fee);
        BigDecimal totalAmount = transaction.getAmount().add(fee);

        Account originatingAccount = transaction.getOriginatingAccount();
        Account resultingAccount = transaction.getResultingAccount();

        originatingAccount.withdrawMoney(totalAmount);

        resultingAccount.deposit(transaction.getAmount());

        Bank bank = originatingAccount.getBank();
        bank.setTotalTransactionFeeAmount(fee);
        bank.setTotalTransferAmount(transaction.getAmount());

        return transactionRepository.save(transaction);
    }

    @Override
    public BigDecimal calculateFee(Transaction transaction) {
        Bank bank = transaction.getOriginatingAccount().getBank();
        BigDecimal feeValue = bank.getTransactionFeeValue();

        if (bank.getTransactionFeeType().equals(TransactionFeeType.PERCENTAGE))
        {
            feeValue = transaction.getAmount().multiply(bank.getTransactionFeeValue());
        }

        return feeValue;
    }

    @Override
    public Transaction getById(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Integer accountId) {
        return transactionRepository.findTransactionsByAccountId(accountId);
    }
}