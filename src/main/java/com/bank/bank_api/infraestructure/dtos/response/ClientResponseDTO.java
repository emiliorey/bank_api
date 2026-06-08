package com.bank.bank_api.infraestructure.dtos.response;

public record ClientResponseDTO(
        String uid,
        String branchUid,
        String name,
        String address,
        String du
) {
}
