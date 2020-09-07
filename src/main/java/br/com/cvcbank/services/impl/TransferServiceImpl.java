package br.com.cvcbank.services.impl;

import br.com.cvcbank.configurations.security.utils.AppSecurityContext;
import br.com.cvcbank.converters.TransferConverter;
import br.com.cvcbank.dtos.BalanceUpdate;
import br.com.cvcbank.dtos.CreateTransferDTO;
import br.com.cvcbank.dtos.TransferDTO;
import br.com.cvcbank.dtos.TransferSummary;
import br.com.cvcbank.exceptions.NotFoundException;
import br.com.cvcbank.repositories.TransferRepository;
import br.com.cvcbank.services.BalanceService;
import br.com.cvcbank.services.FeeService;
import br.com.cvcbank.services.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final FeeService feeService;
    private final TransferRepository transferRepository;
    private final TransferConverter transferConverter;
    private final AppSecurityContext appSecurityContext;
    private final BalanceService balanceService;

    @Override
    public TransferDTO create(CreateTransferDTO createTransferDTO) {
        var transfer = transferConverter.dtoToEntity(createTransferDTO);

        transfer.setScheduledAt(LocalDateTime.now());

        BigDecimal feeValue = feeService.calculateByTransfer(
                new TransferSummary(transfer.getTransferAmount(),
                        transfer.getScheduledAt(),
                        transfer.getTransferDate()));

        transfer.setOriginId(appSecurityContext.getCurrentAccountId());
        transfer.setFee(feeValue);
        transfer.setAmount(transfer.getTransferAmount().add(transfer.getFee()));

        transfer = transferRepository.save(transfer);

        balanceService.updateBalance(
                new BalanceUpdate(transfer.getBeneficiaryId(),
                        transfer.getOriginId(),
                        transfer.getTransferAmount(),
                        transfer.getAmount()));

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
        return transferRepository.findAllByOriginId(appSecurityContext.getCurrentAccountId())
                .stream().map(transferConverter::entityToDTO).collect(Collectors.toList());
    }
}
