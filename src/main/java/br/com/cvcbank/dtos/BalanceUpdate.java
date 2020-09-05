package br.com.cvcbank.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceUpdate {
    private Long originAccountId;
    private Long destinyAccountId;
    private BigDecimal transferAmount;
    private BigDecimal total;
}