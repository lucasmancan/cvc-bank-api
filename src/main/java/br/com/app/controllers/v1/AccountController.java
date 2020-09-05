package br.com.app.controllers.v1;

import br.com.app.dtos.AccountDTO;
import br.com.app.services.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ApiOperation(value = "Criação de Conta.")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        return accountService.create(accountDTO);
    }

    @GetMapping
    @ApiOperation(value = "Busca de informações da conta logada.")
    public AccountDTO findById() {
        return accountService.findActiveAccount();
    }
}
