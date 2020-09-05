package br.com.cvcbank.services;

import br.com.cvcbank.entities.Account;

public interface AccountValidationService {
    void validate(Account account);
}
