package br.com.cvcbank.services;

import br.com.cvcbank.dtos.CreateTransferDTO;
import br.com.cvcbank.dtos.TransferDTO;

import java.util.List;


public interface TransferService {

    /**
     * Create a Transfer and apply validations
     *
     * @param createTransferDTO
     * @return
     */
    TransferDTO create(CreateTransferDTO createTransferDTO);

    TransferDTO findById(Long id);

    List<TransferDTO> findAll();
}
