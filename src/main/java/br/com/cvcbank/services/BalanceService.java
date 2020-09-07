package br.com.cvcbank.services;

import br.com.cvcbank.dtos.BalanceUpdate;

public interface BalanceService {

    /**
     * Update balance of origin and beneficiary account and apply some transaction validations
     *
     * @param balanceUpdate credit exchange information
     */
    void updateBalance(BalanceUpdate balanceUpdate);
}
