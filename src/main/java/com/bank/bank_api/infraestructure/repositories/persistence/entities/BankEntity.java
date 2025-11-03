package com.bank.bank_api.infraestructure.repositories.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bank",
        indexes = {
                @Index(name = "IX_BANK_UID", columnList = "uid", unique = true)
        },
        uniqueConstraints = @UniqueConstraint(name="BANK_UK", columnNames={"address", "name"})
        )
public class BankEntity extends BaseEntity{

    @Column
    private String name;

    @Column
    private String address;

}
