package br.com.app.converters.impl;

import br.com.app.converters.AccountConverter;
import br.com.app.dtos.AccountDTO;
import br.com.app.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverterImpl implements AccountConverter {

    @Override
    public Account dtoToEntity(AccountDTO accountDTO) {
        var account = Account.builder()
                .document(accountDTO.getDocument())
                .type(accountDTO.getType())
                .number(accountDTO.getNumber())
                .updatedAt(accountDTO.getUpdatedAt())
                .password(accountDTO.getPassword())
                .build();

        account.setId(accountDTO.getId());
        return account;
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
