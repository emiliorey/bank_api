package com.bank.bank_api.infraestructure.repositories.persistence.repositories;

import com.bank.bank_api.infraestructure.repositories.persistence.entities.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Long> {
    Optional<BankEntity> findByUid(String uid);
    boolean existsByNameAndAddress(String name, String address);
}
