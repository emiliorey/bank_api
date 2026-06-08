package com.bank.bank_api.infraestructure.adapters;

import com.bank.bank_api.aplication.ports.out.BankBranchRepositoryPort;
import com.bank.bank_api.domain.entities.BankBranch;
import com.bank.bank_api.infraestructure.mappers.BankBranchMapper;
import com.bank.bank_api.infraestructure.repositories.persistence.repositories.BankBranchRepository;
import com.bank.bank_api.infraestructure.repositories.persistence.repositories.BankRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class BankBranchRepositoryAdapter implements BankBranchRepositoryPort {

    private final BankBranchRepository bankBranchRepository;
    private final BankBranchMapper bankBranchMapper;
    private final BankRepository bankRepository;

    @Override
    public BankBranch save(BankBranch bankBranch) {
        log.info("BankBranchRepositoryAdapter.save ...");
        var entity = bankBranchMapper.toEntity(bankBranch);
        entity.setBank(bankRepository.findByUid(bankBranch.bank().uid())
                .orElseThrow(() -> new EntityNotFoundException("Bank not found: " + bankBranch.bank().uid())));
        return bankBranchMapper.toDomain(bankBranchRepository.save(entity));
    }

    @Override
    public Optional<BankBranch> findByUid(String uid) {
        log.info("BankBranchRepositoryAdapter.findByUid uid={}", uid);
        return bankBranchRepository.findByUid(uid).map(bankBranchMapper::toDomain);
    }

    @Override
    public void delete(String uid) {
        log.info("BankBranchRepositoryAdapter.delete (soft) uid={}", uid);
        bankBranchRepository.findByUid(uid).ifPresentOrElse(entity -> {
            entity.setEnabled(false);
            bankBranchRepository.save(entity);
        }, () -> {
            throw new EntityNotFoundException("BankBranch not found: " + uid);
        });
    }
}
