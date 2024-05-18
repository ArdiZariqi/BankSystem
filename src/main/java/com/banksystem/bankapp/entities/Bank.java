package com.banksystem.bankapp.entities;

import com.banksystem.bankapp.enums.TransactionFeeType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private TransactionFeeType transactionFeeType;

    @Column(name = "transaction_fee_value", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal transactionFeeValue;

    @Column(name = "total_transaction_fee_amount", columnDefinition = "DECIMAL(20, 2) DEFAULT 0.00")
    private BigDecimal totalTransactionFeeAmount;

    @Column(name = "total_transfer_amount", columnDefinition = "DECIMAL(20, 2) DEFAULT 0.00")
    private BigDecimal totalTransferAmount;

    @OneToMany(mappedBy = "bank", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Account> accounts;

    // Constructors, Getters, and Setters

    public Bank(){

    }

    public Bank(Integer id, String name, TransactionFeeType transactionFeeType, BigDecimal transactionFeeValue, BigDecimal totalTransactionFeeAmount, BigDecimal totalTransferAmount) {
        this.id = id;
        this.name = name;
        this.transactionFeeType = transactionFeeType;
        this.transactionFeeValue = transactionFeeValue;
        this.totalTransactionFeeAmount = totalTransactionFeeAmount;
        this.totalTransferAmount = totalTransferAmount;
    }

    public TransactionFeeType getTransactionFeeType() {
        return transactionFeeType;
    }

    public void setTransactionFeeType(TransactionFeeType transactionFeeType) {
        this.transactionFeeType = transactionFeeType;
    }

    public BigDecimal getTransactionFeeValue() {
        if (transactionFeeType.equals(TransactionFeeType.PERCENTAGE)){
            return transactionFeeValue.divide(BigDecimal.valueOf(100));
        }
        return transactionFeeValue;
    }

    public void setTransactionFeeValue(BigDecimal transactionFeeValue) {
        this.transactionFeeValue = transactionFeeValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalTransactionFeeAmount() {
        return totalTransactionFeeAmount;
    }

    public void setTotalTransactionFeeAmount(BigDecimal totalTransactionFeeAmount) {
        this.totalTransactionFeeAmount = totalTransactionFeeAmount.add(totalTransactionFeeAmount);
    }

    public BigDecimal getTotalTransferAmount() {
        return totalTransferAmount;
    }

    public void setTotalTransferAmount(BigDecimal totalTransferAmount) {
        this.totalTransferAmount = totalTransferAmount.add(totalTransferAmount);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void updateTotalTransactionFeeAmount(BigDecimal fee) {
        totalTransactionFeeAmount = totalTransactionFeeAmount.add(fee);
    }

    public void updateTotalTransferAmount(BigDecimal amount) {
        totalTransferAmount = totalTransferAmount.add(amount);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", transactionFeeType=" + transactionFeeType +
                ", transactionFeeAmount=" + transactionFeeValue +
                ", totalTransactionFeeAmount=" + totalTransactionFeeAmount +
                ", totalTransferAmount=" + totalTransferAmount +
                ", accounts=" + accounts +
                '}';
    }

    public void addAccount(Account tempAccount) {
        if (accounts == null){
            accounts = new ArrayList<>();
        }

        accounts.add(tempAccount);

        tempAccount.setBank(this);
    }
}
