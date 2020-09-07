package br.com.cvcbank.services.impl;

import br.com.cvcbank.configurations.security.exceptions.AuthenticationNotFoundException;
import br.com.cvcbank.configurations.security.utils.AppSecurityContext;
import br.com.cvcbank.converters.AccountConverter;
import br.com.cvcbank.dtos.AccountDTO;
import br.com.cvcbank.dtos.CreateAccountDTO;
import br.com.cvcbank.exceptions.NotFoundException;
import br.com.cvcbank.repositories.AccountRepository;
import br.com.cvcbank.services.AccountService;
import br.com.cvcbank.services.AccountValidationService;
import br.com.cvcbank.services.PasswordEncoderService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountValidationService accountValidationService;
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final PasswordEncoderService passwordEncoderService;
    private final AppSecurityContext appSecurityContext;

    @Override
    public AccountDTO findById(Long id) {
        return accountRepository.findById(id)
                .map(accountConverter::entityToDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public AccountDTO create(CreateAccountDTO accountDTO) {
        accountValidationService.validate(accountDTO);

        var entity = accountConverter.dtoToEntity(accountDTO);
        entity.setNumber(generateAccountNumber());
        entity.setPassword(passwordEncoderService.encode(entity.getPassword()));
        return accountConverter.entityToDTO(accountRepository.save(entity));
    }

    @Override
    public String generateAccountNumber() {
        return RandomStringUtils.randomNumeric(6);
    }

    @Override
    public AccountDTO findActiveAccount() {
        return accountRepository.findById(appSecurityContext.getCurrentAccountId())
                .map(accountConverter::entityToDTO)
                .orElseThrow(AuthenticationNotFoundException::new);
    }
}
