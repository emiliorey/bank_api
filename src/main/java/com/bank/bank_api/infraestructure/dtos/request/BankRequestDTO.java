package com.bank.bank_api.infraestructure.dtos.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankRequestDTO {

    @Column
    @NotBlank(message = "Name is required")
    private String name;

    @Column
    @NotBlank
    private String address;
}
