package com.bank.bank_api.infraestructure.repositories.persistence.repositories;

import com.bank.bank_api.infraestructure.repositories.persistence.entities.BankBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankBranchRepository extends JpaRepository<BankBranchEntity, Long> {
    Optional<BankBranchEntity> findByUid(String uid);
}
