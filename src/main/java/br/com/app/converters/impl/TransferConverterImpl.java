package br.com.app.converters.impl;

import br.com.app.converters.TransferConverter;
import br.com.app.dtos.CreateTransferDTO;
import br.com.app.dtos.TransferDTO;
import br.com.app.entities.Transfer;
import org.springframework.stereotype.Component;

@Component
public class TransferConverterImpl implements TransferConverter {
    @Override
    public Transfer dtoToEntity(TransferDTO transferDTO) {
        var transfer = Transfer.builder()
                .amount(transferDTO.getAmount())
                .beneficiaryId(transferDTO.getBeneficiaryId())
                .fee(transferDTO.getFee())
                .transferAmount(transferDTO.getTransferAmount())
                .scheduledAt(transferDTO.getScheduledAt())
                .transferDate(transferDTO.getTransferDate())
                .build();

        transfer.setId(transferDTO.getId());
        return transfer;
    }

    @Override
    public TransferDTO entityToDTO(Transfer transfer) {
        return TransferDTO.builder()
                .amount(transfer.getAmount())
                .beneficiaryId(transfer.getBeneficiaryId())
                .fee(transfer.getFee())
                .id(transfer.getId())
                .transferAmount(transfer.getTransferAmount())
                .scheduledAt(transfer.getScheduledAt())
                .transferDate(transfer.getTransferDate())
                .build();
    }

    @Override
    public Transfer dtoToEntity(CreateTransferDTO z) {
        return Transfer.builder()
                .beneficiaryId(z.getBeneficiaryId())
                .transferAmount(z.getTransferAmount())
                .transferDate(z.getTransferDate())
                .build();
    }
}
