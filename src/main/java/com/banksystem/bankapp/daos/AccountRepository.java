package com.banksystem.bankapp.daos;

import com.banksystem.bankapp.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
