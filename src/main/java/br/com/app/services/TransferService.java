package br.com.app.services;

import br.com.app.dtos.CreateTransferDTO;
import br.com.app.dtos.TransferDTO;

import java.util.List;

public interface TransferService {
    TransferDTO create(CreateTransferDTO createTransferDTO);

    TransferDTO findById(Long id);

    List<TransferDTO> findAll();
}
