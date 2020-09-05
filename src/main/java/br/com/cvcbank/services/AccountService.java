package br.com.cvcbank.services;

import br.com.cvcbank.dtos.AccountDTO;

public interface AccountService {
    AccountDTO findById(Long id);

    AccountDTO create(AccountDTO accountDTO);

    String generateAccountNumber();

    AccountDTO findActiveAccount();
}
