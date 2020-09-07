package br.com.cvcbank.services.impl;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.caelum.stella.validation.Validator;
import br.com.cvcbank.dtos.CreateAccountDTO;
import br.com.cvcbank.entities.AccountType;
import br.com.cvcbank.exceptions.ValidationException;
import br.com.cvcbank.repositories.AccountRepository;
import br.com.cvcbank.services.AccountValidationService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountValidationServiceImpl implements AccountValidationService {

    private final AccountRepository accountRepository;

    @Override
    public void validate(CreateAccountDTO account) {
        var cleanDocument = StringUtils.trimToNull(account.getDocument());

        if (accountRepository.findByDocument(account.getDocument()).isPresent()) {
            throw new ValidationException("Account already exists.");
        }

        if (account.getPassword().isEmpty() || account.getPassword().length() < 6) {
            throw new ValidationException("Password should have at least 6 chars.");
        }

        if (cleanDocument == null) {
            throw new ValidationException("Document is not valid.");
        }

        try {

            Validator<String> validator;

            if (AccountType.individual.equals(account.getType())) {
                validator = new CPFValidator();
            } else {
                validator = new CNPJValidator();
            }

            validator.assertValid(account.getDocument());
        } catch (InvalidStateException e) {
            throw new ValidationException("Document is not valid.");
        }
    }
}
