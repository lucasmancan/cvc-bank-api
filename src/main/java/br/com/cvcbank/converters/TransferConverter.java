package br.com.cvcbank.converters;

import br.com.cvcbank.dtos.CreateTransferDTO;
import br.com.cvcbank.dtos.TransferDTO;
import br.com.cvcbank.entities.Transfer;

public interface TransferConverter extends Converter<Transfer, TransferDTO> {
    Transfer dtoToEntity(CreateTransferDTO z);
}
