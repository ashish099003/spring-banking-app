package com.banking.mapper;


import com.banking.dto.TransactionResponseDTO;
import com.banking.model.Transaction;

public class TransactionMapper {

    public static TransactionResponseDTO toDTO(Transaction transaction) {
        return TransactionResponseDTO.builder()
                .transactionId(transaction.getId())
                .fromAccountNumber(transaction.getFromAccount().getAccountNumber())
                .toAccountNumber(transaction.getToAccount().getAccountNumber())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .build();
    }
}

