package br.com.app.services;

import br.com.app.dtos.AccountDTO;

public interface AccountService {
    AccountDTO findById(Long id);

    AccountDTO create(AccountDTO accountDTO);

    String generateAccountNumber();

    AccountDTO findActiveAccount();
}
