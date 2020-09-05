package br.com.cvcbank.converters;


import br.com.cvcbank.dtos.AccountDTO;
import br.com.cvcbank.dtos.CreateAccountDTO;
import br.com.cvcbank.entities.Account;

public interface AccountConverter extends Converter<Account, AccountDTO> {
    Account dtoToEntity(CreateAccountDTO accountDTO);
}
