package com.bank.bank_api.infraestructure.repositories.persistence.repositories;

import com.bank.bank_api.infraestructure.repositories.persistence.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByUid(String uid);
}
