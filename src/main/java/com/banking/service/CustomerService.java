package com.banking.service;

import com.banking.dto.CustomerDTO;
import com.banking.dto.CustomerRequestDTO;
import com.banking.model.Customer;
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

    public CustomerDTO createCustomer(CustomerRequestDTO createCustomerRequest) {
        // Check if customer already exists
        System.out.println(createCustomerRequest.getLastName());

        customerRepository.findByEmail(createCustomerRequest.getEmailId())
                .ifPresent(existing -> {
                    throw new RuntimeException("Customer with email " + createCustomerRequest.getEmailId() + " already exists.");
                });

        // Map DTO to Entity
        Customer customer = new Customer();
        System.out.println(createCustomerRequest.getEmailId());
        System.out.println(createCustomerRequest.getFirstName());
        System.out.println(createCustomerRequest.getLastName());
        customer.setFirstName(createCustomerRequest.getFirstName());
        customer.setLastName(createCustomerRequest.getLastName());
        customer.setEmail(createCustomerRequest.getEmailId());

        // Save customer
        Customer savedCustomer = customerRepository.save(customer);

        // Map Entity back to DTO
        return new CustomerDTO(
                savedCustomer.getId(),
                savedCustomer.getFirstName(),
                savedCustomer.getLastName(),
                savedCustomer.getEmail()
        );
    }
}

