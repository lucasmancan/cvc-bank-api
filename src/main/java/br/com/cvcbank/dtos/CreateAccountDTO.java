package br.com.cvcbank.dtos;

import br.com.cvcbank.entities.AccountType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel("InitialAccount")
public class CreateAccountDTO {
    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    private String password;
    @NotNull(message = "Account type is required")
    private AccountType type;
    @NotNull(message = "Document number is required")
    private String document;

    @NotNull(message = "initial balance is required")
    private BigDecimal initialBalance;

    public String getDocument() {
        return document != null ? document.replaceAll("\\D", "") : null;
    }
}
