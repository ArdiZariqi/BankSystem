package com.banksystem.bankapp;

import com.banksystem.bankapp.entities.Bank;
import com.banksystem.bankapp.entities.Account;
import com.banksystem.bankapp.entities.Transaction;
import com.banksystem.bankapp.enums.TransactionFeeType;
import com.banksystem.bankapp.service.interfaces.IBankService;
import com.banksystem.bankapp.service.interfaces.IAccountService;
import com.banksystem.bankapp.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp implements CommandLineRunner {

    @Autowired
    private IBankService iBankService;

    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private ITransactionService iTransactionService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Create Bank");
            System.out.println("2. Create Account");
            System.out.println("3. Perform Transaction");
            System.out.println("4. Withdraw");
            System.out.println("5. Deposit");
            System.out.println("6. List Transactions");
            System.out.println("7. Check Account Balance");
            System.out.println("8. List Bank Accounts");
            System.out.println("9. Check Bank Fees");
            System.out.println("10. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createBank(scanner);
                    break;
                case 2:
                    createAccount(scanner);
                    break;
                case 3:
                    performTransaction(scanner);
                    break;
                case 4:
                    withdraw(scanner);
                    break;
                case 5:
                    deposit(scanner);
                    break;
                case 6:
                    listTransactions(scanner);
                    break;
                case 7:
                    checkAccountBalance(scanner);
                    break;
                case 8:
                    listBankAccounts(scanner);
                    break;
                case 9:
                    checkBankFees(scanner);
                    break;
                case 10:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void createBank(Scanner scanner) {
        System.out.println("Enter bank name:");
        String name = scanner.nextLine();
        System.out.println("Enter fee type (PERCENTAGE or FLAT VALUE): ");
        TransactionFeeType feeType;

        try {
            feeType = TransactionFeeType.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid fee type provided.");
        }

        System.out.println("Enter fee amount:");
        BigDecimal fee = scanner.nextBigDecimal();

        Bank bank = new Bank();
        bank.setName(name);
        bank.setTransactionFeeType(feeType);
        bank.setTransactionFeeValue(fee);
        iBankService.save(bank);

        System.out.println("Bank created.");
    }

    private void createAccount(Scanner scanner) {
        System.out.println("Enter account user name:");
        String userName = scanner.nextLine();
        System.out.println("Enter bank id:");
        Integer bankId = scanner.nextInt();

        Bank bank = iBankService.getById(bankId);
        if (bank == null) {
            System.out.println("Bank not found.");
            return;
        }

        Account account = new Account();
        account.setUserName(userName);
        account.setBalance(BigDecimal.ZERO);  // Set initial balance to 0
        account.setBank(bank);
        iAccountService.save(account);

        System.out.println("Account created.");
    }

    private void performTransaction(Scanner scanner) {
        System.out.println("Enter originating account id:");
        Integer originatingAccountId = scanner.nextInt();
        System.out.println("Enter resulting account id:");
        Integer resultingAccountId = scanner.nextInt();
        System.out.println("Enter amount:");
        BigDecimal amount = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.println("Enter reason:");
        String reason = scanner.nextLine();

        Account originatingAccount = iAccountService.getById(originatingAccountId);
        Account resultingAccount = iAccountService.getById(resultingAccountId);

        if (originatingAccount == null || resultingAccount == null) {
            System.out.println("Account not found.");
            return;
        }

        Transaction transaction = new Transaction(amount, originatingAccount, resultingAccount, reason, BigDecimal.ZERO);

        BigDecimal fee = iTransactionService.calculateFee(transaction);
        transaction.setFee(fee);
        BigDecimal totalAmount = transaction.getAmount().add(fee);

        // Perform the transaction
        iTransactionService.save(transaction);

        // Update the bank's total transaction fee amount and total transfer amount
        Bank originatingBank = originatingAccount.getBank();
        originatingBank.setTotalTransactionFeeAmount(
                originatingBank.getTotalTransactionFeeAmount().add(fee)
        );
        originatingBank.setTotalTransferAmount(
                originatingBank.getTotalTransferAmount().add(transaction.getAmount())
        );

        // Save the bank's updated totals
        iBankService.save(originatingBank);

        System.out.println("Transaction completed.");
    }

    private void withdraw(Scanner scanner) {
        System.out.println("Enter account id:");
        Integer accountId = scanner.nextInt();
        System.out.println("Enter amount:");
        BigDecimal amount = scanner.nextBigDecimal();

        Account account = iAccountService.getById(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        iAccountService.withdraw(accountId, amount);

        Transaction transaction = new Transaction(amount.negate(), account, account, "Withdrawal", BigDecimal.ZERO);
        iTransactionService.save(transaction);

        System.out.println("Withdrawal completed.");
    }

    private void deposit(Scanner scanner) {
        System.out.println("Enter account id:");
        Integer accountId = scanner.nextInt();
        System.out.println("Enter amount:");
        BigDecimal amount = scanner.nextBigDecimal();

        Account account = iAccountService.getById(accountId);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        iAccountService.depositMoney(accountId, amount);

        Transaction transaction = new Transaction(amount, account, account, "DEPOSIT", BigDecimal.ZERO);
        iTransactionService.save(transaction);

        System.out.println("Deposit completed.");
    }

    private void listTransactions(Scanner scanner) {
        System.out.println("Enter account id:");
        Integer accountId = scanner.nextInt();

        List<Transaction> transactions = iTransactionService.getTransactionsByAccountId(accountId);
        transactions.forEach(System.out::println);
    }

    private void checkAccountBalance(Scanner scanner) {
        System.out.println("Enter account id:");
        Integer accountId = scanner.nextInt();

        Account account = iAccountService.getById(accountId);
        if (account != null) {
            System.out.println("Balance: " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private void listBankAccounts(Scanner scanner) {
        System.out.println("Enter bank id:");
        Integer bankId = scanner.nextInt();

        Bank bank = iBankService.getById(bankId);
        if (bank != null) {
            bank.getAccounts().forEach(System.out::println);
        } else {
            System.out.println("Bank not found.");
        }
    }

    private void checkBankFees(Scanner scanner) {
        System.out.println("Enter bank id:");
        Integer bankId = scanner.nextInt();

        Bank bank = iBankService.getById(bankId);
        if (bank != null) {
            System.out.println("Total Transaction Fee Amount: " + bank.getTotalTransactionFeeAmount());
            System.out.println("Total Transfer Amount: " + bank.getTotalTransferAmount());
        } else {
            System.out.println("Bank not found.");
        }
    }
}
