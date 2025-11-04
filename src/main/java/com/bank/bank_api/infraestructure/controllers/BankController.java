package com.bank.bank_api.infraestructure.controllers;

import com.bank.bank_api.aplication.ports.in.CreateBankUseCase;
import com.bank.bank_api.aplication.ports.in.GetBankUserCase;
import com.bank.bank_api.domain.entities.Bank;
import com.bank.bank_api.infraestructure.dtos.request.BankRequestDTO;
import com.bank.bank_api.infraestructure.dtos.response.BankResponseDTO;
import com.bank.bank_api.infraestructure.handler.error.ErrorResponse;
import com.bank.bank_api.infraestructure.mappers.BankMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bank")
@AllArgsConstructor
@Validated
@Slf4j
public class BankController {

    private final CreateBankUseCase createBankUseCase;
    private final GetBankUserCase getBankUserCase;
    private final BankMapper bankMapper;

    @PostMapping
    public ResponseEntity<BankResponseDTO> createBank(@Valid @RequestBody BankRequestDTO bankRequestDTO) {
        log.info("BankController.createBank .....");
        return ResponseEntity.ok(bankMapper.toDTO(
                createBankUseCase.create(bankMapper.toDomain(bankRequestDTO)))
        );
    }

    @GetMapping(value = "/{uid}")
    public ResponseEntity<BankResponseDTO> findBank(@PathVariable String uid) {
        log.info("BankController.findBank .....");
        Optional<Bank> optionalBank = getBankUserCase.findByUid(uid);
        return optionalBank
                .map(bank -> ResponseEntity.ok(bankMapper.toDTO(bank)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/self/{uid}")
    @CircuitBreaker(name="selfService")
    public ResponseEntity<BankResponseDTO> getBank(@PathVariable String uid) {
        log.info("BankController.getBank .....");
        return ResponseEntity.ok(bankMapper.toDTO(getBankUserCase.getByUid(uid)));
    }

//    public ResponseEntity<BankResponseDTO> fallbackMethod(String uid, Throwable throwable) {
//        return ResponseEntity
//                .status(HttpStatus.SERVICE_UNAVAILABLE)
//                .body(new BankResponseDTO(uid, null, null));
//    }
}
