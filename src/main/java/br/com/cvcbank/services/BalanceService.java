package br.com.cvcbank.services;

import br.com.cvcbank.entities.Transfer;

public interface BalanceService {
    void updateBalance(Transfer transfer);
}
