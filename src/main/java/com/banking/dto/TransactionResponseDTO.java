package com.banking.dto;

import com.banking.model.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDTO {
    private Long transactionId;
    private Long fromAccountNumber;
    private Long toAccountNumber;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private TransactionType type;
    private String description;
}

