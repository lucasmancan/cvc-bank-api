package br.com.cvcbank.services;

import br.com.cvcbank.entities.Transfer;

import java.math.BigDecimal;

public interface FeeService {
    BigDecimal calculateByTransfer(Transfer transfer);
}
