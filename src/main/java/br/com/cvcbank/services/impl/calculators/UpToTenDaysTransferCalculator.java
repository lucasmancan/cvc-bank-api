package br.com.cvcbank.services.impl.calculators;

import java.math.BigDecimal;

public class UpToTenDaysTransferCalculator extends Calculator {

    private final long differenceInDays;

    public UpToTenDaysTransferCalculator(long differenceInDays) {
        this.differenceInDays = differenceInDays;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return new BigDecimal(12).multiply(BigDecimal.valueOf(differenceInDays));
    }
}
