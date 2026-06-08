package com.bank.bank_api.aplication.ports.out;

import com.bank.bank_api.domain.entities.Client;

public interface ClientRepositoryPort {
    Client save(Client client);
}
