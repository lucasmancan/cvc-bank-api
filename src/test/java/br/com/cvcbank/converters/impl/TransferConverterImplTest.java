package br.com.cvcbank.converters.impl;

import br.com.cvcbank.dtos.CreateTransferDTO;
import br.com.cvcbank.dtos.TransferDTO;
import br.com.cvcbank.entities.Transfer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {TransferConverterImpl.class})
class TransferConverterImplTest {

    @Autowired
    TransferConverterImpl transferConverter;

    private Transfer mockTransfer() {
        Transfer transfer = new Transfer();
        transfer.setTransferDate(LocalDateTime.now());
        transfer.setId(1L);
        transfer.setTransferAmount(new BigDecimal(1000));
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setBeneficiaryId(1L);
        transfer.setFee(new BigDecimal(1000));
        transfer.setAmount(new BigDecimal(1000));
        return transfer;
    }

    private TransferDTO mockTransferDTO() {
        TransferDTO transfer = new TransferDTO();
        transfer.setTransferDate(LocalDateTime.now());
        transfer.setId(1L);
        transfer.setTransferAmount(new BigDecimal(1000));
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setBeneficiaryId(1L);
        transfer.setFee(new BigDecimal(1000));
        transfer.setAmount(new BigDecimal(1000));
        return transfer;
    }

    @Test
    void shouldConvertDtoToEntity() {
        var dto = mockTransferDTO();

        Transfer transfer = transferConverter.dtoToEntity(dto);

        assertEquals(transfer.getTransferAmount(), dto.getTransferAmount());
        assertEquals(transfer.getAmount(), dto.getTransferAmount());
        assertEquals(transfer.getId(), dto.getId());
        assertEquals(transfer.getScheduledAt(), dto.getScheduledAt());
        assertEquals(transfer.getBeneficiaryId(), dto.getBeneficiaryId());
        assertEquals(transfer.getFee(), dto.getFee());
        assertEquals(transfer.getAmount(), dto.getAmount());
    }

    @Test
    void shouldConvertEntityToDto() {
        var entity = mockTransfer();

        TransferDTO dto = transferConverter.entityToDTO(entity);

        assertEquals(dto.getTransferAmount(), entity.getTransferAmount());
        assertEquals(dto.getAmount(), entity.getTransferAmount());
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getScheduledAt(), entity.getScheduledAt());
        assertEquals(dto.getBeneficiaryId(), entity.getBeneficiaryId());
        assertEquals(dto.getFee(), entity.getFee());
        assertEquals(dto.getAmount(), entity.getAmount());
    }

    @Test
    void shouldConvertCreateTransferDtoToEntity() {
        CreateTransferDTO createTransferDTO = new CreateTransferDTO();
        createTransferDTO.setBeneficiaryId(1L);
        createTransferDTO.setTransferDate(LocalDateTime.now());
        createTransferDTO.setTransferAmount(new BigDecimal(1000));

        Transfer transfer = transferConverter.dtoToEntity(createTransferDTO);

        assertEquals(createTransferDTO.getTransferAmount(), transfer.getTransferAmount());
        assertEquals(createTransferDTO.getBeneficiaryId(), transfer.getBeneficiaryId());
        assertEquals(createTransferDTO.getTransferDate(), transfer.getTransferDate());
    }
}