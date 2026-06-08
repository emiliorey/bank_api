package com.bank.bank_api.infraestructure.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record ClientRequestDTO(
        @NotBlank String branchUid,
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String du
) {
}
