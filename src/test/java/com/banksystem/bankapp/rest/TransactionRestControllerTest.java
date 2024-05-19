package com.banksystem.bankapp.rest;

import com.banksystem.bankapp.entities.Transaction;
import com.banksystem.bankapp.service.interfaces.ITransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransactionRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ITransactionService transactionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        TransactionRestController transactionRestController = new TransactionRestController(transactionService);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionRestController).build();
    }

    @Test
    public void testCreateTransaction() throws Exception {
        Transaction transaction = new Transaction(BigDecimal.valueOf(100), null, null, "Test transaction", BigDecimal.ZERO);
        when(transactionService.save(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100, \"reason\": \"Test transaction\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.reason").value("Test transaction"));

        verify(transactionService, times(1)).save(any(Transaction.class));
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void testGetTransactionById() throws Exception {
        Transaction transaction = new Transaction(BigDecimal.valueOf(150), null, null, "Test transaction", BigDecimal.ZERO);

        when(transactionService.getById(1)).thenReturn(transaction);

        mockMvc.perform(get("/api/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(150))
                .andExpect(jsonPath("$.reason").value("Test transaction"));

        verify(transactionService, times(1)).getById(1);
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        Transaction transaction1 = new Transaction(BigDecimal.valueOf(200), null, null, "Transaction 1", BigDecimal.ZERO);
        Transaction transaction2 = new Transaction(BigDecimal.valueOf(300), null, null, "Transaction 2", BigDecimal.ZERO);
        when(transactionService.getAll()).thenReturn(List.of(transaction1, transaction2));

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(200))
                .andExpect(jsonPath("$[0].reason").value("Transaction 1"))
                .andExpect(jsonPath("$[1].amount").value(300))
                .andExpect(jsonPath("$[1].reason").value("Transaction 2"));

        verify(transactionService, times(1)).getAll();
        verifyNoMoreInteractions(transactionService);
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        mockMvc.perform(delete("/api/transactions/1"))
                .andExpect(status().isNoContent());

        verify(transactionService, times(1)).delete(1);
        verifyNoMoreInteractions(transactionService);
    }
}