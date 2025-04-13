package com.banking.controller;


import com.banking.dto.TransactionResponseDTO;
import com.banking.model.Account;
import com.banking.service.AccountService;
import com.banking.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor // <-- This makes constructor injection easy with Lombok
public class AccountController {

    @Autowired
    private AccountService accountService;
    private final TransactionService transactionService;


    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable Long accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Account> getAccount(@PathVariable("id") Long id) {
//        Account account = new Account();
//        account.setId(id);
////        account.setName("Test Account");
//        account.setBalance(BigDecimal.valueOf(5000.0));
//        return ResponseEntity.ok(account);
//    }


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

}
