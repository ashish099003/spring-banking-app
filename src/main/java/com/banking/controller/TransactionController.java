package com.banking.controller;

import com.banking.dto.TransactionRequestDTO;
import com.banking.dto.TransactionResponseDTO;
import com.banking.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDTO> transfer(@RequestBody TransactionRequestDTO request) {
        return ResponseEntity.ok(transactionService.transfer(request));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactionsForAccount(accountId));
    }
}

