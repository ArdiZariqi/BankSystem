package com.banksystem.bankapp.rest;

import com.banksystem.bankapp.entities.Bank;
import com.banksystem.bankapp.enums.TransactionFeeType;
import com.banksystem.bankapp.service.interfaces.IBankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BankRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IBankService bankService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        BankRestController bankRestController = new BankRestController(bankService);
        mockMvc = MockMvcBuilders.standaloneSetup(bankRestController).build();
    }

    @Test
    public void testCreateBank() throws Exception {
        Bank bank = new Bank(1, "Bank A", TransactionFeeType.PERCENTAGE, BigDecimal.valueOf(150), BigDecimal.ZERO, BigDecimal.ZERO);
        when(bankService.save(any(Bank.class))).thenReturn(bank);

        mockMvc.perform(post("/api/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Bank A\", \"transactionFeeType\": \"PERCENTAGE\", \"transactionFeeValue\": 150, \"totalTransactionFeeAmount\": 0, \"totalTransferAmount\": 0 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bank A"))
                .andExpect(jsonPath("$.transactionFeeType").value("PERCENTAGE"))
                .andExpect(jsonPath("$.transactionFeeValue").value(1.5));

        verify(bankService, times(1)).save(any(Bank.class));
        verifyNoMoreInteractions(bankService);
    }

    @Test
    public void testGetBankById() throws Exception {
        Bank bank = new Bank(1, "Bank B", TransactionFeeType.FLATVALUE, BigDecimal.valueOf(10), BigDecimal.ZERO, BigDecimal.ZERO);
        when(bankService.getById(1)).thenReturn(bank);

        mockMvc.perform(get("/api/banks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bank B"))
                .andExpect(jsonPath("$.transactionFeeType").value("FLATVALUE"))
                .andExpect(jsonPath("$.transactionFeeValue").value(10));

        verify(bankService, times(1)).getById(1);
        verifyNoMoreInteractions(bankService);
    }

    @Test
    public void testGetAllBanks() throws Exception {
        Bank bank1 = new Bank(1, "Bank C", TransactionFeeType.FLATVALUE, BigDecimal.valueOf(2.5), BigDecimal.ZERO, BigDecimal.ZERO);
        when(bankService.getAll()).thenReturn(Collections.singletonList(bank1));

        mockMvc.perform(get("/api/banks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bank C"))
                .andExpect(jsonPath("$[0].transactionFeeType").value("FLATVALUE"))
                .andExpect(jsonPath("$[0].transactionFeeValue").value(2.5));

        verify(bankService, times(1)).getAll();
        verifyNoMoreInteractions(bankService);
    }

    @Test
    public void testDeleteBank() throws Exception {
        mockMvc.perform(delete("/api/banks/1"))
                .andExpect(status().isNoContent());

        verify(bankService, times(1)).delete(1);
        verifyNoMoreInteractions(bankService);
    }
}