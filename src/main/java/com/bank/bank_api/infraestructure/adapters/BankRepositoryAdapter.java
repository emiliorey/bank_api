package com.bank.bank_api.infraestructure.adapters;

import com.bank.bank_api.aplication.ports.out.BankRepositoryPort;
import com.bank.bank_api.domain.entities.Bank;
import com.bank.bank_api.domain.exceptions.DuplicateBankException;
import com.bank.bank_api.infraestructure.mappers.BankMapper;
import com.bank.bank_api.infraestructure.repositories.externalservices.BankFeignClient;
import com.bank.bank_api.infraestructure.repositories.persistence.entities.BankEntity;
import com.bank.bank_api.infraestructure.repositories.persistence.repositories.BankRepository;
import com.bank.bank_api.infraestructure.validators.bank.BankExistValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class BankRepositoryAdapter implements BankRepositoryPort {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;
    private final BankExistValidator bankValidators;
    private final BankFeignClient bankFeignClient;

    @Override
    public Bank save(Bank bank) {
        log.info("BankRepositoryAdapter.save ...");
        if(bankValidators.validate(bank)) {
            throw new DuplicateBankException("Bank name %s and address %s is duplicated".formatted(bank.name(), bank.address()));
        }
        return bankMapper.toDomain(bankRepository.save(
                bankMapper.toEntity(bank)));
    }

    @Override
    public Optional<Bank> findByUid(String uid) {
        log.info("BankRepositoryAdapter.findByUid ...");
        final BankEntity bankEntity = bankRepository.findByUid(uid)
                .orElseThrow(() -> new EntityNotFoundException("Bank %s not found".formatted(uid)));

        return Optional.of(bankMapper.toDomain(bankEntity));

    }

    @Override
    public Bank getByUid(String uid) {
        log.info("BankRepositoryAdapter.getByUid ...");
        return bankMapper.toDomain(bankFeignClient.getByUid(uid));
    }

}
