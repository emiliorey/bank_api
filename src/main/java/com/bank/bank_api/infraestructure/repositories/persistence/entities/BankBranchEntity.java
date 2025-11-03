package com.bank.bank_api.infraestructure.repositories.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_branch",
        indexes = {
                @Index(name = "ix_bank_uid", columnList = "uid", unique = true),
                @Index(name = "ix_bank_name", columnList = "name", unique = true)
        },
        uniqueConstraints = @UniqueConstraint(name="ADDRESS_UK", columnNames="address")
)
public class BankBranchEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(nullable = false, name = "bank_id", foreignKey = @ForeignKey(name="FK_branch_bank"))
    private BankEntity bank;
    @Column
    private String name;
    @Column
    private String address;

}
