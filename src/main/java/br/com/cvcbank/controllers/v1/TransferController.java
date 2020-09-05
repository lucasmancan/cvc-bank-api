package br.com.cvcbank.controllers.v1;

import br.com.cvcbank.dtos.CreateTransferDTO;
import br.com.cvcbank.dtos.TransferDTO;
import br.com.cvcbank.services.TransferService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/transfers")
@AllArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    @ApiOperation(value = "Criação de transferências.",
            authorizations = {@Authorization(value = "jwtToken")})
    @ResponseStatus(HttpStatus.CREATED)
    public TransferDTO create(@RequestBody @Valid CreateTransferDTO createTransferDTO) {
        return transferService.create(createTransferDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca de transferência por identificação.",
            authorizations = {@Authorization(value = "jwtToken")})
    public TransferDTO findById(@ApiParam(value = "Transfer identifier", required = true, example = "123"
    ) @PathVariable Long id) {
        return transferService.findById(id);
    }

    @GetMapping
    @ApiOperation(value = "Busca todas as transferências da conta logada.",
            authorizations = {@Authorization(value = "jwtToken")})
    public List<TransferDTO> findAllCurrentAccountTransfers() {
        return transferService.findAll();
    }
}
