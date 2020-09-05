package br.com.app.services.impl;

import br.com.app.entities.Transfer;
import br.com.app.services.FeeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
public class FeeServiceImpl implements FeeService {

    @Override
    public BigDecimal calculateByTransfer(Transfer transfer) {
        BigDecimal fee = BigDecimal.ZERO;

        long differenceInDays = ChronoUnit.DAYS.between(transfer.getScheduledAt(), transfer.getTransferDate());

        if (differenceInDays == 0) {
            fee = fee.add(new BigDecimal(3).add((BigDecimal.valueOf(0.03).multiply(transfer.getTransferAmount()))));
        } else if (differenceInDays <= 10) {
            fee = fee.add(new BigDecimal(12).multiply(BigDecimal.valueOf(differenceInDays)));
        } else if (differenceInDays <= 20) {
            fee = fee.add(BigDecimal.valueOf(0.08).multiply(transfer.getTransferAmount()));
        } else if (differenceInDays <= 30) {
            fee = fee.add(BigDecimal.valueOf(0.06).multiply(transfer.getTransferAmount()));
        } else if (differenceInDays <= 40) {
            fee = fee.add(BigDecimal.valueOf(0.04).multiply(transfer.getTransferAmount()));
        } else {
            fee = fee.add(BigDecimal.valueOf(0.02).multiply(transfer.getTransferAmount()));
        }

        return fee;
    }
}
