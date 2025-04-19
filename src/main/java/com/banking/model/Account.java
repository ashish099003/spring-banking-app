package com.banking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter // Lombok will generate the getter methods
@Setter // Lombok will generate the setter methods
@AllArgsConstructor // Lombok will generate a constructor with all arguments
@NoArgsConstructor  // Lombok will generate a no-argument constructor
@ToString // Lombok will generate the toString() method
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountNumber;
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonBackReference
    private Customer customer;

}
