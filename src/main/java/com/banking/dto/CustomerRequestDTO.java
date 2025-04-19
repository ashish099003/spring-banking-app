package com.banking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDTO {

    private String firstName;
    private String lastName;

    @NotBlank(message = "Email must not be blank")
    private String emailId;
}
