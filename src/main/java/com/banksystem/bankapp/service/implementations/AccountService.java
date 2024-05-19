package com.banksystem.bankapp.service.implementations;

import com.banksystem.bankapp.daos.AccountRepository;
import com.banksystem.bankapp.entities.Account;
import com.banksystem.bankapp.entities.Bank;
import com.banksystem.bankapp.enums.TransactionFeeType;
import com.banksystem.bankapp.exception.CustomException;
import com.banksystem.bankapp.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
        Optional<Account> result = accountRepository.findById(id);
        Account theAccount = null;
        if (result.isPresent()) {
            theAccount = result.get();
        } else {
            throw new CustomException("Account with ID " + id + " not found");
        }
        return theAccount;
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
            Bank bank = account.getBank();
            BigDecimal fee = calculateFee(amount, bank);
            BigDecimal totalAmount = amount.add(fee);

            if (account.getBalance().compareTo(totalAmount) < 0) {
                throw new CustomException("Insufficient funds in the account. Balance must cover the withdrawal amount plus the fee.");
            }

            account.withdrawMoney(amount);
            return accountRepository.save(account);
        } else {
            throw new CustomException("Account not found.");
        }
    }

    private BigDecimal calculateFee(BigDecimal amount, Bank bank) {
        BigDecimal feeValue = bank.getTransactionFeeValue();
        if (bank.getTransactionFeeType().equals(TransactionFeeType.PERCENTAGE)) {
            feeValue = amount.multiply(bank.getTransactionFeeValue());
        }
        return feeValue;
    }

    @Override
    public Account depositMoney(Integer accountId, BigDecimal amount) {
        Account account = getById(accountId);
        if (account != null) {
            account.deposit(amount);
            return accountRepository.save(account);
        } else {
            throw new CustomException("Account not found.");
        }
    }
}