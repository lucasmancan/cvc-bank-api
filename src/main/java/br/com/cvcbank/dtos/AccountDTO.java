package br.com.cvcbank.dtos;

import br.com.cvcbank.entities.AccountType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("Account")
public class AccountDTO {
    private Long id;
    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    private String password;
    private String number;
    @NotNull(message = "Account type is required")
    private AccountType type;
    @NotNull(message = "Document number is required")
    private String document;
    private BigDecimal balance;
    private LocalDateTime updatedAt;

    public String getDocument() {
        return document != null ? document.replaceAll("\\D", "") : null;
    }

}
