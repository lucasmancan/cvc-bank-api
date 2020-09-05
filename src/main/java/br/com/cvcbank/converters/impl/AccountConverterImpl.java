package br.com.cvcbank.converters.impl;

import br.com.cvcbank.converters.AccountConverter;
import br.com.cvcbank.dtos.AccountDTO;
import br.com.cvcbank.dtos.CreateAccountDTO;
import br.com.cvcbank.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverterImpl implements AccountConverter {

    @Override
    public Account dtoToEntity(AccountDTO accountDTO) {
        return Account.builder()
                .document(accountDTO.getDocument())
                .type(accountDTO.getType())
                .number(accountDTO.getNumber())
                .updatedAt(accountDTO.getUpdatedAt())
                .password(accountDTO.getPassword())
                .id(accountDTO.getId())
                .build();
    }

    @Override
    public Account dtoToEntity(CreateAccountDTO accountDTO) {
        return Account.builder()
                .document(accountDTO.getDocument())
                .type(accountDTO.getType())
                .password(accountDTO.getPassword())
                .balance(accountDTO.getInitialBalance())
                .build();
    }

    @Override
    public AccountDTO entityToDTO(Account account) {
        return AccountDTO.builder()
                .updatedAt(account.getUpdatedAt())
                .id(account.getId())
                .document(account.getDocument())
                .type(account.getType())
                .number(account.getNumber())
                .balance(account.getBalance())
                .build();
    }
}
