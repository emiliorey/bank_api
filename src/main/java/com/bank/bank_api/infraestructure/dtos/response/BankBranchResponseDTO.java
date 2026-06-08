package com.bank.bank_api.infraestructure.dtos.response;

public record BankBranchResponseDTO(
        String uid,
        String bankUid,
        String name,
        String address
) {
}
