package com.banking.controller;

import com.banking.model.Account;
import com.banking.service.AccountService;
import com.banking.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionService transactionService;

    @Test
    void getAccountByAccountNumber() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber(12345L); // Make sure this is a Long
        account.setBalance(new BigDecimal("1000.00"));

        when(accountService.getAccountByNumber(12345L)).thenReturn(account);

        mockMvc.perform(get("/api/accounts/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(12345))
                .andExpect(jsonPath("$.balance").value(1000.00));
    }
}
