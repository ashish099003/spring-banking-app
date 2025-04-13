package com.banking.model;

import com.banking.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Getter // Lombok will generate the getter methods
@Setter // Lombok will generate the setter methods
@AllArgsConstructor // Lombok will generate a constructor with all arguments
@NoArgsConstructor  // Lombok will generate a no-argument constructor
@ToString // Lombok will generate the toString() method
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account fromAccount;

    @ManyToOne
    private Account toAccount;

    private BigDecimal amount;
    private LocalDateTime timestamp;


    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String description;

    // Getters and Setters
}
