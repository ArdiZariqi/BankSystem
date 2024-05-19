package com.banksystem.bankapp.service.implementations;

import com.banksystem.bankapp.daos.TransactionRepository;
import com.banksystem.bankapp.entities.Account;
import com.banksystem.bankapp.entities.Bank;
import com.banksystem.bankapp.entities.Transaction;
import com.banksystem.bankapp.enums.TransactionFeeType;
import com.banksystem.bankapp.exception.CustomException;
import com.banksystem.bankapp.service.interfaces.IAccountService;
import com.banksystem.bankapp.service.interfaces.IBankService;
import com.banksystem.bankapp.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    private TransactionRepository transactionRepository;
    private IAccountService accountService;
    private IBankService bankService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, IAccountService accountService, IBankService bankService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.bankService = bankService;
    }

    @Override
    public Transaction save(Transaction transaction) {
        BigDecimal fee;

        Account originatingAccount = transaction.getOriginatingAccount();
        Account resultingAccount = transaction.getResultingAccount();
        Bank bank = resultingAccount.getBank();

        if (originatingAccount == null) {
            fee = BigDecimal.ZERO;
            resultingAccount.deposit(transaction.getAmount());
            accountService.save(resultingAccount);
        } else {
            fee = calculateFee(transaction);
            transaction.setFee(fee);
            BigDecimal totalAmount = transaction.getAmount().add(fee);

            if (originatingAccount.getBalance().compareTo(totalAmount) < 0) {
                throw new CustomException("Insufficient funds in the originating account.");
            }

            originatingAccount.withdrawMoney(totalAmount);
            resultingAccount.deposit(transaction.getAmount());

            accountService.save(originatingAccount);
            accountService.save(resultingAccount);
        }

        bank.setTotalTransferAmount(bank.getTotalTransferAmount().add(transaction.getAmount()));
        bank.setTotalTransactionFeeAmount(bank.getTotalTransactionFeeAmount().add(fee));
        bankService.save(bank);

        return transactionRepository.save(transaction);
    }

    @Override
    public BigDecimal calculateFee(Transaction transaction) {
        Bank bank = transaction.getOriginatingAccount().getBank();
        BigDecimal feeValue = bank.getTransactionFeeValue();

        if (bank.getTransactionFeeType().equals(TransactionFeeType.PERCENTAGE)) {
            feeValue = transaction.getAmount().multiply(bank.getTransactionFeeValue());
        }

        return feeValue;
    }

    @Override
    public Transaction getById(Integer id) {
        Optional<Transaction> result = transactionRepository.findById(id);
        Transaction theTransaction = null;
        if (result.isPresent()) {
            theTransaction = result.get();
        } else {
            throw new CustomException("Transaction with ID " + id + " not found");
        }
        return theTransaction;
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
