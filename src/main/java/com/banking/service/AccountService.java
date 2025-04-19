package com.banking.service;

import com.banking.model.Account;
import com.banking.model.Customer;
import com.banking.repository.AccountRepository;
import com.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Account getAccountByNumber(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public String transferMoney(Long fromAccountNumber, Long toAccountNumber, BigDecimal amount) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("From account not found"));
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        // Business logic for transferring money (balance checks, etc.)

        return "Transfer Successful";
    }

    public Account createAccountForCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        System.out.println(customer.getEmail());
        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountNumber(Long.valueOf(generateAccountNumber()));
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    private String generateAccountNumber() {
        int min = 100000;
        int max = 999999;
        int randomNum = (int)(Math.random() * (max - min + 1)) + min;
        return String.valueOf(randomNum);
    }

}
