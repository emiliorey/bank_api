package com.bank.bank_api.infraestructure.controllers;

import com.bank.bank_api.aplication.ports.in.CreateClientUseCase;
import com.bank.bank_api.domain.entities.BankBranch;
import com.bank.bank_api.domain.entities.Client;
import com.bank.bank_api.infraestructure.dtos.request.ClientRequestDTO;
import com.bank.bank_api.infraestructure.dtos.response.ClientResponseDTO;
import com.bank.bank_api.infraestructure.mappers.ClientMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
@Slf4j
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final ClientMapper clientMapper;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientRequestDTO request) {
        log.info("ClientController.createClient branchUid={}", request.branchUid());
        Client client = createClientUseCase.create(
                new Client(null, new BankBranch(request.branchUid(), null, null, null),
                        request.name(), request.address(), request.du()));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.toDTO(client));
    }
}
