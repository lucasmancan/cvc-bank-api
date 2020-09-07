package br.com.cvcbank.services;

import br.com.cvcbank.dtos.CreateAccountDTO;

public interface AccountValidationService {
    void validate(CreateAccountDTO dto);
}
