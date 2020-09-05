package br.com.cvcbank.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("TransferSummary")
public class CreateTransferDTO implements Serializable {

    @NotNull(message = "Transfer amount is required")
    @Min(value = 0, message = "Transfer amount must be greather than 0.")
    private BigDecimal transferAmount;

    @NotNull(message = "Beneficiary is required")
    private Long beneficiaryId;

    @NotNull(message = "Transfer date is required")
    private LocalDateTime transferDate;
}