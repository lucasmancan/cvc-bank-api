package br.com.cvcbank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public final class TransferSummary {
    private final BigDecimal transferAmount;
    private final LocalDateTime scheduledAt;
    private final LocalDateTime transferDate;
}
