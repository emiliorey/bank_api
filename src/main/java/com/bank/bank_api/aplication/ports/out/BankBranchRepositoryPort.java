package com.bank.bank_api.aplication.ports.out;

import com.bank.bank_api.domain.entities.BankBranch;

import java.util.Optional;

public interface BankBranchRepositoryPort {
    BankBranch save(BankBranch bankBranch);
    Optional<BankBranch> findByUid(String uid);
    void delete(String uid);
}
