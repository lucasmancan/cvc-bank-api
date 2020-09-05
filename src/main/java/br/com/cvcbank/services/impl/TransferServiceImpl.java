package br.com.cvcbank.services.impl;

import br.com.cvcbank.converters.TransferConverter;
import br.com.cvcbank.dtos.CreateTransferDTO;
import br.com.cvcbank.dtos.TransferDTO;
import br.com.cvcbank.exceptions.NotFoundException;
import br.com.cvcbank.repositories.TransferRepository;
import br.com.cvcbank.services.FeeService;
import br.com.cvcbank.services.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final FeeService feeService;
    private final TransferRepository transferRepository;
    private final TransferConverter transferConverter;

    @Override
    public TransferDTO create(CreateTransferDTO createTransferDTO) {
        var transfer = transferConverter.dtoToEntity(createTransferDTO);

        final BigDecimal feeValue = feeService.calculateByTransfer(transfer);

        transfer.setFee(feeValue);
        transfer.setAmount(transfer.getTransferAmount().add(transfer.getFee()));
        transfer = transferRepository.save(transfer);

        return transferConverter.entityToDTO(transfer);
    }

    @Override
    public TransferDTO findById(Long id) {
        return transferRepository.findById(id)
                .map(transferConverter::entityToDTO)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<TransferDTO> findAll() {
        return transferRepository.findAll().stream().map(transferConverter::entityToDTO).collect(Collectors.toList());
    }
}
