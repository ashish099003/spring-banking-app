package com.banking.service;

import com.banking.dto.TransactionRequestDTO;
import com.banking.dto.TransactionResponseDTO;
import com.banking.exception.ResourceNotFoundException;
import com.banking.mapper.TransactionMapper;
import com.banking.model.Account;
import com.banking.model.Transaction;
import com.banking.model.enums.TransactionType;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public TransactionResponseDTO transfer(TransactionRequestDTO request) {
        if (request.getFromAccountId().equals(request.getToAccountId())) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        Account from = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Account to = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance.");
        }

        // Update balances
        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        accountRepository.save(from);
        accountRepository.save(to);

        Transaction txn = new Transaction();
        txn.setFromAccount(from);
        txn.setToAccount(to);
        txn.setAmount(request.getAmount());
        txn.setTimestamp(LocalDateTime.now());
        txn.setType(TransactionType.TRANSFER);
        txn.setDescription(request.getDescription());

        Transaction saved = transactionRepository.save(txn);

        return new TransactionResponseDTO(
                saved.getId(),
                from.getAccountNumber(),
                to.getAccountNumber(),
                saved.getAmount(),
                saved.getTimestamp(),
                saved.getType(),
                saved.getDescription()
        );
    }


    public List<TransactionResponseDTO> getTransactionsForAccount(Long accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        List<Transaction> transactions = transactionRepository.findByFromAccountIdOrToAccountId(account.getId(), account.getId());

        return transactions.stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }
}


