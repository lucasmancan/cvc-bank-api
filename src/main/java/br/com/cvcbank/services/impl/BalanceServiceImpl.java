package br.com.cvcbank.services.impl;

import br.com.cvcbank.configurations.security.exceptions.AuthenticationNotFoundException;
import br.com.cvcbank.configurations.security.utils.AppSecurityContext;
import br.com.cvcbank.dtos.BalanceUpdate;
import br.com.cvcbank.exceptions.ValidationException;
import br.com.cvcbank.repositories.AccountRepository;
import br.com.cvcbank.services.BalanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final AppSecurityContext appSecurityContext;
    private final AccountRepository accountRepository;

    @Override
    public void updateBalance(BalanceUpdate balanceUpdate) {

        if (balanceUpdate.getBeneficiaryId().equals(appSecurityContext.getCurrentAccountId())) {
            throw new ValidationException("Transfer to the same account is not allowed.");
        }

        var account = accountRepository.findById(appSecurityContext.getCurrentAccountId())
                .orElseThrow(AuthenticationNotFoundException::new);

        if (account.getBalance().compareTo(balanceUpdate.getAmount()) < 0) {
            throw new ValidationException("Account does not have enough balance to complete the transaction");
        }

        account.setBalance(account.getBalance().subtract(balanceUpdate.getAmount()));

        var beneficiaryAccount = accountRepository.findById(balanceUpdate.getBeneficiaryId())
                .orElseThrow(AuthenticationNotFoundException::new);

        beneficiaryAccount.setBalance(beneficiaryAccount.getBalance().add(balanceUpdate.getTransferAmount()));

        accountRepository.saveAll(Arrays.asList(account, beneficiaryAccount));
    }
}
