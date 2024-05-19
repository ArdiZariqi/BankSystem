package com.banksystem.bankapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount", nullable = false, columnDefinition = "DECIMAL(20, 2)")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "originating_account_id")
    @JsonBackReference
    private Account originatingAccount;

    @ManyToOne
    @JoinColumn(name = "resulting_account_id")
    @JsonBackReference
    private Account resultingAccount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "fee", columnDefinition = "DECIMAL(20, 2) DEFAULT 0.00")
    private BigDecimal fee;

    // Constructors, Getters, and Setters
    public Transaction() {
    }

    public Transaction(BigDecimal amount, Account resultingAccount, String reason) {
        this.amount = amount;
        this.resultingAccount = resultingAccount;
        this.reason = reason;
    }

    public Transaction(BigDecimal amount, Account originatingAccount, Account resultingAccount, String reason, BigDecimal fee) {
        this.amount = amount;
        this.originatingAccount = originatingAccount;
        this.resultingAccount = resultingAccount;
        this.reason = reason;
        this.fee = fee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getOriginatingAccount() {
        return originatingAccount;
    }

    public void setOriginatingAccount(Account originatingAccount) {
        this.originatingAccount = originatingAccount;
    }

    public Account getResultingAccount() {
        return resultingAccount;
    }

    public void setResultingAccount(Account resultingAccount) {
        this.resultingAccount = resultingAccount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", originatingAccount=" + (originatingAccount != null ? originatingAccount.getId() : null) +
                ", resultingAccount=" + (resultingAccount != null ? resultingAccount.getId() : null) +
                ", reason='" + reason + '\'' +
                ", fee=" + fee +
                '}';
    }
}
