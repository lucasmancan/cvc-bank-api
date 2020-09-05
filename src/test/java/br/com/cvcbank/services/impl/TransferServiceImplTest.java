package br.com.cvcbank.services.impl;

import br.com.cvcbank.configurations.security.utils.AppSecurityContext;
import br.com.cvcbank.converters.TransferConverter;
import br.com.cvcbank.dtos.CreateTransferDTO;
import br.com.cvcbank.dtos.TransferDTO;
import br.com.cvcbank.entities.Transfer;
import br.com.cvcbank.repositories.TransferRepository;
import br.com.cvcbank.services.BalanceService;
import br.com.cvcbank.services.FeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {TransferServiceImpl.class})
class TransferServiceImplTest {

    @Autowired
    TransferServiceImpl transferService;

    @MockBean
    FeeService feeService;

    @MockBean
    TransferConverter transferConverter;

    @MockBean
    BalanceService balanceService;

    @MockBean
    AppSecurityContext appSecurityContext;

    @MockBean
    TransferRepository repository;

    public TransferDTO mockTransferDTO() {
        TransferDTO transfer = new TransferDTO();
        transfer.setId(1L);
        transfer.setAmount(new BigDecimal(1000));
        transfer.setTransferAmount(new BigDecimal(1000));
        transfer.setFee(new BigDecimal(1000));
        transfer.setBeneficiaryId(1L);
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setTransferDate(LocalDateTime.now());
        return transfer;
    }

    public Transfer mockTransfer() {
        Transfer transfer = new Transfer();
        transfer.setId(1L);
        transfer.setAmount(new BigDecimal(1000));
        transfer.setTransferAmount(new BigDecimal(1000));
        transfer.setFee(new BigDecimal(1000));
        transfer.setBeneficiaryId(1L);
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setTransferDate(LocalDateTime.now());
        return transfer;
    }

    public CreateTransferDTO mockCreateTransfer() {
        CreateTransferDTO transfer = new CreateTransferDTO();
        transfer.setBeneficiaryId(1L);
        transfer.setTransferAmount(new BigDecimal(1000));
        transfer.setTransferDate(LocalDateTime.now());
        return transfer;
    }

    @BeforeEach
    void setup() {
        when(feeService.calculateByTransfer(any())).thenReturn(new BigDecimal(100));
        when(transferConverter.dtoToEntity(any(CreateTransferDTO.class))).thenReturn(mockTransfer());
        when(transferConverter.entityToDTO(any(Transfer.class))).thenReturn(mockTransferDTO());
    }


    @Test
    void shouldCreateTransfer() {
        when(repository.save(any())).thenReturn(mockTransfer());

        var createTransfer = mockCreateTransfer();

        TransferDTO transferDTO = transferService.create(createTransfer);

        assertEquals(transferDTO.getTransferAmount(), createTransfer.getTransferAmount());
        assertEquals(transferDTO.getBeneficiaryId(), createTransfer.getBeneficiaryId());
    }

    @Test
    void shouldFindTransferById() {
        var transfer = mockTransfer();
        when(repository.findById(any())).thenReturn(Optional.of(transfer));

        TransferDTO transferDTO = transferService.findById(1L);

        assertEquals(transferDTO.getTransferAmount(), transfer.getTransferAmount());
        assertEquals(transferDTO.getAmount(), transfer.getTransferAmount());
        assertEquals(transferDTO.getId(), transfer.getId());
        assertEquals(transferDTO.getBeneficiaryId(), transfer.getBeneficiaryId());
        assertEquals(transferDTO.getFee(), transfer.getFee());
        assertEquals(transferDTO.getAmount(), transfer.getAmount());
    }

    @Test
    void shouldFindAllCurrentAccountTransfers() {
        when(repository.findAllByOriginId(any())).thenReturn(Arrays.asList(mockTransfer(), mockTransfer()));
        List<TransferDTO> transferList = transferService.findAll();
        assertEquals(2, transferList.size());
    }
}