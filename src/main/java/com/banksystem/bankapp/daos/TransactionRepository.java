package com.banksystem.bankapp.daos;

import com.banksystem.bankapp.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.originatingAccount.id = :accountId OR t.resultingAccount.id = :accountId")
    List<Transaction> findTransactionsByAccountId(@Param("accountId") Integer accountId);
}