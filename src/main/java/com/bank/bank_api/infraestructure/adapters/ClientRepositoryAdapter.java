package com.bank.bank_api.infraestructure.adapters;

import com.bank.bank_api.aplication.ports.out.ClientRepositoryPort;
import com.bank.bank_api.domain.entities.Client;
import com.bank.bank_api.infraestructure.mappers.ClientMapper;
import com.bank.bank_api.infraestructure.repositories.persistence.repositories.BankBranchRepository;
import com.bank.bank_api.infraestructure.repositories.persistence.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final BankBranchRepository bankBranchRepository;

    @Override
    public Client save(Client client) {
        log.info("ClientRepositoryAdapter.save ...");
        var entity = clientMapper.toEntity(client);
        entity.setBranch(bankBranchRepository.findByUid(client.branch().uid())
                .orElseThrow(() -> new EntityNotFoundException("BankBranch not found: " + client.branch().uid())));
        return clientMapper.toDomain(clientRepository.save(entity));
    }
}
