package com.bank.bank_api.infraestructure.mappers;

import com.bank.bank_api.domain.entities.BankBranch;
import com.bank.bank_api.infraestructure.dtos.response.BankBranchResponseDTO;
import com.bank.bank_api.infraestructure.repositories.persistence.entities.BankBranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BankMapper.class})
public interface BankBranchMapper {

    @Mapping(target = "uid", source = "uid")
    BankBranch toDomain(BankBranchEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    BankBranchEntity toEntity(BankBranch bankBranch);

    @Mapping(target = "bankUid", source = "bank.uid")
    BankBranchResponseDTO toDTO(BankBranch bankBranch);
}
