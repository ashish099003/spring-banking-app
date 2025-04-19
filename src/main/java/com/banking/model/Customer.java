package com.banking.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter // Lombok will generate the getter methods
@Setter // Lombok will generate the setter methods
@AllArgsConstructor // Lombok will generate a constructor with all arguments
@NoArgsConstructor  // Lombok will generate a no-argument constructor
@ToString // Lombok will generate the toString() method
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    // Optionally, you can also define a one-to-many relationship back to accounts
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Account> accounts;


    // Getters and Setters
}



