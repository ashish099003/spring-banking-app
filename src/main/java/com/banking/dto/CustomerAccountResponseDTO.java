package com.banking.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class CustomerAccountResponseDTO {
    private Long customerId;
    private Long accountNumber;
    private BigDecimal balance;


    public CustomerAccountResponseDTO(Long customerId, Long accountNumber, BigDecimal balance) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}
