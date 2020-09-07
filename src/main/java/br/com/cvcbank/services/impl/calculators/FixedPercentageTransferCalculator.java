package br.com.cvcbank.services.impl.calculators;

import br.com.cvcbank.exceptions.ValidationException;

import java.math.BigDecimal;

public class FixedPercentageTransferCalculator extends Calculator {

    private final long differenceInDays;

    public FixedPercentageTransferCalculator(long differenceInDays) {
        this.differenceInDays = differenceInDays;
    }

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        double percentage;

        if (differenceInDays <= 20) {
            percentage = 0.08;
        } else if (differenceInDays <= 30) {
            percentage = 0.06;
        } else if (differenceInDays <= 40) {
            percentage = 0.04;
        } else if (differenceInDays > 40 && amount.compareTo(BigDecimal.valueOf(100000)) >= 0) {
            percentage = 0.02;
        } else {
            // TODO GAP in DOCS, check with project manager
            throw new ValidationException("Transfer is not in a mapped fee interval");
        }

        return BigDecimal.valueOf(percentage).multiply(amount);
    }
}
