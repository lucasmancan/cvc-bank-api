package br.com.app.controllers.v1;

import br.com.app.dtos.CreateTransferDTO;
import br.com.app.dtos.TransferDTO;
import br.com.app.services.TransferService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Criação de transferências.")
    @ResponseStatus(HttpStatus.CREATED)
    public TransferDTO create(@RequestBody @Valid CreateTransferDTO createTransferDTO) {
        return transferService.create(createTransferDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca de transferência por id.")
    public TransferDTO findById(@PathVariable Long id) {
        return transferService.findById(id);
    }

    @GetMapping
    @ApiOperation(value = "Busca todas as transferências.")
    public List<TransferDTO> findAll() {
        return transferService.findAll();
    }
}
