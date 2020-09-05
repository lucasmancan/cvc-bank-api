package br.com.app.services;

import br.com.app.entities.Transfer;

import java.math.BigDecimal;

public interface FeeService {
    BigDecimal calculateByTransfer(Transfer transfer);
}
