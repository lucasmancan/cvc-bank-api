package br.com.cvcbank.services.impl;

import br.com.cvcbank.configurations.security.exceptions.AuthenticationNotFoundException;
import br.com.cvcbank.configurations.security.utils.AppSecurityContext;
import br.com.cvcbank.entities.Transfer;
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
    public void updateBalance(Transfer transfer) {

        if (transfer.getBeneficiaryId().equals(appSecurityContext.getCurrentAccountId())) {
            throw new ValidationException("Transfer to the same account is not allowed.");
        }

        var account = accountRepository.findById(appSecurityContext.getCurrentAccountId())
                .orElseThrow(AuthenticationNotFoundException::new);

        if (account.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new ValidationException("Account does not have enough balance to complete the transaction");
        }

        account.setBalance(account.getBalance().subtract(transfer.getAmount()));

        var beneficiaryAccount = accountRepository.findById(transfer.getBeneficiaryId())
                .orElseThrow(AuthenticationNotFoundException::new);

        beneficiaryAccount.setBalance(beneficiaryAccount.getBalance().add(transfer.getTransferAmount()));

        accountRepository.saveAll(Arrays.asList(account, beneficiaryAccount));
    }
}
