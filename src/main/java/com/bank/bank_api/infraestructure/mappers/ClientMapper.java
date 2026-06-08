package com.bank.bank_api.infraestructure.mappers;

import com.bank.bank_api.domain.entities.Client;
import com.bank.bank_api.infraestructure.dtos.response.ClientResponseDTO;
import com.bank.bank_api.infraestructure.repositories.persistence.entities.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BankBranchMapper.class})
public interface ClientMapper {

    Client toDomain(ClientEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    ClientEntity toEntity(Client client);

    @Mapping(target = "branchUid", source = "branch.uid")
    ClientResponseDTO toDTO(Client client);
}
