package br.com.cvcbank.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("Transfer")
public class TransferDTO {
    private Long id;
    private BigDecimal transferAmount;
    private BigDecimal fee;
    private BigDecimal amount;
    private Long beneficiaryId;
    private LocalDateTime scheduledAt;
    private LocalDateTime transferDate;
}
