package br.com.cvcbank.services.impl.calculators;

import java.math.BigDecimal;

public abstract class Calculator {
    public static Calculator getInstance(long differenceInDays) {
        if (differenceInDays == 0) {
            return new SameDayCalculator();
        } else if (differenceInDays <= 10) {
            return new UpToTenDaysTransferCalculator(differenceInDays);
        } else {
            return new FixedPercentageTransferCalculator(differenceInDays);
        }
    }

    public abstract BigDecimal calculate(BigDecimal amount);
}
