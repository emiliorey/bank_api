package com.bank.bank_api.infraestructure.controllers;

import com.bank.bank_api.aplication.ports.in.CreateBankBranchUseCase;
import com.bank.bank_api.aplication.ports.in.DeleteBankBranchUseCase;
import com.bank.bank_api.domain.entities.Bank;
import com.bank.bank_api.domain.entities.BankBranch;
import com.bank.bank_api.infraestructure.dtos.request.BankBranchRequestDTO;
import com.bank.bank_api.infraestructure.dtos.response.BankBranchResponseDTO;
import com.bank.bank_api.infraestructure.mappers.BankBranchMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branches")
@AllArgsConstructor
@Slf4j
public class BankBranchController {

    private final CreateBankBranchUseCase createBankBranchUseCase;
    private final DeleteBankBranchUseCase deleteBankBranchUseCase;
    private final BankBranchMapper bankBranchMapper;

    @PostMapping
    public ResponseEntity<BankBranchResponseDTO> createBranch(@Valid @RequestBody BankBranchRequestDTO request) {
        log.info("BankBranchController.createBranch bankUid={}", request.bankUid());
        BankBranch branch = createBankBranchUseCase.create(
                new BankBranch(null, new Bank(request.bankUid(), null, null), request.name(), request.address()));
        return ResponseEntity.status(HttpStatus.CREATED).body(bankBranchMapper.toDTO(branch));
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> deleteBranch(@PathVariable String uid) {
        log.info("BankBranchController.deleteBranch uid={}", uid);
        deleteBankBranchUseCase.delete(uid);
        return ResponseEntity.noContent().build();
    }
}
