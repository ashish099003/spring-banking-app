package com.banking.service;

import com.banking.dto.TransactionRequestDTO;
import com.banking.dto.TransactionResponseDTO;
import com.banking.model.Account;
import com.banking.model.Transaction;
import com.banking.model.enums.TransactionType;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock accounts
        fromAccount = new Account();
        fromAccount.setId(1L);
        fromAccount.setAccountNumber(Long.valueOf("12345")); // Changed to String
        fromAccount.setBalance(new BigDecimal("5000.00"));

        toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setAccountNumber(Long.valueOf("67890")); // Changed to String
        toAccount.setBalance(new BigDecimal("2000.00"));
    }

    @Test
    void transfer_Successful() {
        // Arrange: Mock repository calls
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        // Mock account saves after transaction
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            if (account.getId().equals(fromAccount.getId())) {
                fromAccount.setBalance(new BigDecimal("4500.00")); // Decrease sender balance
            } else if (account.getId().equals(toAccount.getId())) {
                toAccount.setBalance(new BigDecimal("2500.00")); // Increase receiver balance
            }
            return account;
        });

        // Mock transaction save
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        // Act: Perform the transfer
        BigDecimal amount = new BigDecimal("500.00");
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setAmount(amount);
        transactionRequestDTO.setFromAccountId(1L);
        transactionRequestDTO.setToAccountId(2L);
        transactionRequestDTO.setDescription("Transfer amount from account 12345 to account 67890");

        TransactionResponseDTO transactionResponseDTO = transactionService.transfer(transactionRequestDTO);

        // Assert: Verify the transaction was successful and account balances updated
        assertNotNull(transactionResponseDTO);
        assertEquals(new BigDecimal("4500.00"), fromAccount.getBalance()); // sender balance decreased
        assertEquals(new BigDecimal("2500.00"), toAccount.getBalance()); // receiver balance increased
    }

    @Test
    void transfer_InsufficientBalance() {
        // Arrange: Set up insufficient balance scenario
        fromAccount.setBalance(new BigDecimal("100.00")); // Insufficient balance for transfer
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        // Act and Assert: Should throw exception due to insufficient balance
        assertThrows(IllegalArgumentException.class, () -> {
            TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
            transactionRequestDTO.setDescription("Test Transfer");
            transactionRequestDTO.setAmount(new BigDecimal(200.0));
            transactionRequestDTO.setFromAccountId(Long.valueOf("1"));
            transactionRequestDTO.setToAccountId(Long.valueOf("2"));
            transactionService.transfer(transactionRequestDTO);
        });
    }
}
