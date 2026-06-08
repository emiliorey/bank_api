package com.bank.bank_api.infraestructure.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record BankBranchRequestDTO(
        @NotBlank String bankUid,
        @NotBlank String name,
        @NotBlank String address
) {
}
