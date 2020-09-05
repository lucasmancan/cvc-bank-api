package br.com.app.services.impl;

import br.com.app.entities.Account;
import br.com.app.entities.AccountType;
import br.com.app.exceptions.ValidationException;
import br.com.app.services.AccountValidationService;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.caelum.stella.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountValidationServiceImpl implements AccountValidationService {

    @Override
    public void validate(Account account) {
        var cleanDocument = StringUtils.trimToNull(account.getDocument());

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
