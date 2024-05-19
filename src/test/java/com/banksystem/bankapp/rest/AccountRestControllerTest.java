package com.banksystem.bankapp.rest;

import com.banksystem.bankapp.entities.Account;
import com.banksystem.bankapp.service.interfaces.IAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IAccountService accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        AccountRestController accountRestController = new AccountRestController(accountService);
        mockMvc = MockMvcBuilders.standaloneSetup(accountRestController).build();
    }

    @Test
    public void testCreateAccount() throws Exception {
        Account account = new Account("John Doe", BigDecimal.valueOf(1000));
        when(accountService.save(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"userName\": \"John Doe\", \"balance\": 1000 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("John Doe"))
                .andExpect(jsonPath("$.balance").value(1000));

        verify(accountService, times(1)).save(any(Account.class));
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testGetAccountById() throws Exception {
        Account account = new Account("Jane Smith", BigDecimal.valueOf(500));
        when(accountService.getById(1)).thenReturn(account);

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Jane Smith"))
                .andExpect(jsonPath("$.balance").value(500));

        verify(accountService, times(1)).getById(1);
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testGetAllAccounts() throws Exception {
        Account account1 = new Account("Alice", BigDecimal.valueOf(200));
        Account account2 = new Account("Bob", BigDecimal.valueOf(300));
        when(accountService.getAll()).thenReturn(Collections.singletonList(account1));

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("Alice"))
                .andExpect(jsonPath("$[0].balance").value(200));

        verify(accountService, times(1)).getAll();
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testDeleteAccount() throws Exception {
        mockMvc.perform(delete("/api/accounts/1"))
                .andExpect(status().isNoContent());

        verify(accountService, times(1)).delete(1);
        verifyNoMoreInteractions(accountService);
    }
}