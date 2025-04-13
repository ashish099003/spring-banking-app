package com.banking.service;

import com.banking.model.Account;
import com.banking.model.Customer;
import com.banking.repository.AccountRepository;
import com.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerById(String emailId) {
        return customerRepository.findByEmail(emailId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
