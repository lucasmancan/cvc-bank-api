package br.com.app.services.impl;

import br.com.app.configurations.security.exceptions.AuthenticationNotFoundException;
import br.com.app.configurations.security.utils.ContextUtils;
import br.com.app.converters.AccountConverter;
import br.com.app.dtos.AccountDTO;
import br.com.app.exceptions.NotFoundException;
import br.com.app.repositories.AccountRepository;
import br.com.app.services.AccountService;
import br.com.app.services.AccountValidationService;
import br.com.app.services.PasswordEncoderService;
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

    @Override
    public AccountDTO findById(Long id) {
        return accountRepository.findById(id)
                .map(accountConverter::entityToDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        var entity = accountConverter.dtoToEntity(accountDTO);
        accountValidationService.validate(entity);
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
        return accountRepository.findById(ContextUtils.currentAccountId())
                .map(accountConverter::entityToDTO)
                .orElseThrow(AuthenticationNotFoundException::new);
    }
}
