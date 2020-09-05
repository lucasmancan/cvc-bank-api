package br.com.cvcbank.services.impl.calculators;

import java.math.BigDecimal;

public class SameDayCalculator extends Calculator {
    @Override
    public BigDecimal calculate(BigDecimal amount) {
        return new BigDecimal(3).add((BigDecimal.valueOf(0.03).multiply(amount)));
    }
}
