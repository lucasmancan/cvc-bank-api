package br.com.app.converters;

import br.com.app.dtos.CreateTransferDTO;
import br.com.app.dtos.TransferDTO;
import br.com.app.entities.Transfer;

public interface TransferConverter extends Converter<Transfer, TransferDTO> {
    Transfer dtoToEntity(CreateTransferDTO z);
}
