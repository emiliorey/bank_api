package com.bank.bank_api.aplication.ports.in;

import com.bank.bank_api.domain.entities.Client;

public interface CreateClientUseCase {
    Client create(Client client);
}
