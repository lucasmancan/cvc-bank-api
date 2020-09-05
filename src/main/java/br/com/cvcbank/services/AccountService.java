package br.com.cvcbank.services;

import br.com.cvcbank.dtos.AccountDTO;
import br.com.cvcbank.dtos.CreateAccountDTO;

public interface AccountService {
    AccountDTO findById(Long id);

    AccountDTO create(CreateAccountDTO accountDTO);

    String generateAccountNumber();

    AccountDTO findActiveAccount();
}
