package com.bank.bank_api.infraestructure.mappers;

import com.bank.bank_api.domain.entities.Bank;
import com.bank.bank_api.infraestructure.dtos.request.BankRequestDTO;
import com.bank.bank_api.infraestructure.dtos.response.BankResponseDTO;
import com.bank.bank_api.infraestructure.repositories.persistence.entities.BankEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMapper {

    Bank toDomain(BankEntity bankEntity);

    BankEntity toEntity(Bank bank);

    BankResponseDTO toDTO(Bank bank);

    Bank toDomain(BankResponseDTO bankResponseDTO);

    Bank toDomain(BankRequestDTO bankRequestDTO);
}
