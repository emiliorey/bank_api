package com.bank.bank_api.aplication.ports.in;

import com.bank.bank_api.domain.entities.BankBranch;

public interface CreateBankBranchUseCase {
    BankBranch create(BankBranch bankBranch);
}
