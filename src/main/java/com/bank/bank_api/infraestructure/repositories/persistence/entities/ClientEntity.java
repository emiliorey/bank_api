package com.bank.bank_api.infraestructure.repositories.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ClientEntity extends BaseEntity{
    @ManyToOne
    @JoinColumn(nullable = false, name = "branch_id", foreignKey = @ForeignKey(name="FK_client_branch"))
    private BankBranchEntity branch;
    @Column
    private String du;
    @Column
    private String address;
}
