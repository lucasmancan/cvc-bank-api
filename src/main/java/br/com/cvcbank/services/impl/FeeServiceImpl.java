package br.com.cvcbank.services.impl;

import br.com.cvcbank.entities.Transfer;
import br.com.cvcbank.services.FeeService;
import br.com.cvcbank.services.impl.calculators.Calculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
public class FeeServiceImpl implements FeeService {

    @Override
    public BigDecimal calculateByTransfer(Transfer transfer) {

        long differenceInDays = ChronoUnit.DAYS.between(transfer.getScheduledAt(), transfer.getTransferDate());

        Calculator calculator = Calculator.getInstance(differenceInDays);

        return calculator.calculate(transfer.getTransferAmount());
    }
}

