package com.banking.mapper;

import com.banking.dto.AccountDTO;
import com.banking.model.Account;
import com.banking.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final CustomerRepository customerRepository;

    public AccountDTO toDto(Account account) {
        return new AccountDTO(account.getId(), account.getAccountNumber(), account.getBalance(), account.getCustomer().getId());
    }

    public Account toEntity(AccountDTO dto) {
        Account account = new Account();
        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(dto.getBalance());
        account.setCustomer(customerRepository.findById(dto.getCustomerId()).orElseThrow());
        return account;
    }
}

