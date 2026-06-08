package com.bank.bank_api.aplication.service;

import com.bank.bank_api.aplication.ports.in.CreateClientUseCase;
import com.bank.bank_api.aplication.ports.out.ClientRepositoryPort;
import com.bank.bank_api.domain.entities.Client;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ClientService implements CreateClientUseCase {

    private final ClientRepositoryPort clientRepositoryPort;

    @Override
    public Client create(Client client) {
        log.info("ClientService.create ...");
        return clientRepositoryPort.save(client);
    }
}
