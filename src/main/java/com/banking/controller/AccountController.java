package com.banking.controller;


import com.banking.dto.*;
import com.banking.model.Account;
import com.banking.model.Customer;
import com.banking.repository.CustomerRepository;
import com.banking.service.AccountService;
import com.banking.service.CustomerService;
import com.banking.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor // <-- This makes constructor injection easy with Lombok
public class AccountController {

    @Autowired
    private final AccountService accountService;
    @Autowired
    private final TransactionService transactionService;
    @Autowired
    private final CustomerService customerService;


    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable Long accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }


    @PostMapping("/transfer")
    public String transferMoney(@RequestParam Long fromAccountNumber,
                                @RequestParam Long toAccountNumber,
                                @RequestParam BigDecimal amount) {
        return accountService.transferMoney(fromAccountNumber, toAccountNumber, amount);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponseDTO>> getAccountTransactions(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionsForAccount(id));
    }
    @PostMapping("/create-customer-account")
    public ResponseEntity<?> createCustomerAndAccount( @Valid @RequestBody CustomerRequestDTO createCustomerRequest) {
        try {
            // Step 1: Create Customer
            CustomerDTO createdCustomer = customerService.createCustomer(createCustomerRequest);
            // Step 2: Create Account for the new Customer
            Account newAccount = accountService.createAccountForCustomerId(createdCustomer.getId());

            // Step 3: Prepare response
            CustomerAccountResponseDTO response = new CustomerAccountResponseDTO(
                    createdCustomer.getId(),
                    newAccount.getAccountNumber(),
                    newAccount.getBalance()
            );

            return ResponseEntity
                    .ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Or return meaningful error body
        }
    }
    @PostMapping(
            value = "/create-account",
            consumes = "application/json",
            produces = "application/json"
    )
    public ResponseEntity<CustomerAccountResponseDTO> createAccount(@RequestBody AccountRequestDTO request) {
        try {
            Account newAccount = accountService.createAccountForCustomerId(request.getCustomerId());

            CustomerAccountResponseDTO response = new CustomerAccountResponseDTO(
                    newAccount.getCustomer().getId(),
                    newAccount.getAccountNumber(),
                    newAccount.getBalance()
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            // Optional: return a proper error object instead of null
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }



}
