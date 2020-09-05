package br.com.cvcbank.controllers.v1;

import br.com.cvcbank.dtos.CreateTransferDTO;
import br.com.cvcbank.dtos.TransferDTO;
import br.com.cvcbank.services.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class TransferControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransferService transferService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach()
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    public CreateTransferDTO mockCreateTransferDTO() {
        CreateTransferDTO dto = new CreateTransferDTO();
        dto.setTransferAmount(new BigDecimal(1000));
        dto.setTransferDate(LocalDateTime.now().plusDays(9));
        dto.setBeneficiaryId(1L);
        return dto;
    }

    public TransferDTO mockTransfer() {
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

    @Test
    @WithMockUser(username = "user", password = "password")
    void shouldCreateNewTransfer() throws Exception {

        var createTransfer = mockCreateTransferDTO();

        TransferDTO transfer = new TransferDTO();
        transfer.setId(1L);
        transfer.setTransferAmount(createTransfer.getTransferAmount());
        transfer.setAmount(createTransfer.getTransferAmount());
        transfer.setFee(new BigDecimal(1000));
        transfer.setBeneficiaryId(createTransfer.getBeneficiaryId());
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setTransferDate(LocalDateTime.now());

        when(transferService.create(any())).thenReturn(transfer);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/transfers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(mockCreateTransferDTO()))).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.beneficiaryId").value(transfer.getBeneficiaryId()))
                .andExpect(jsonPath("$.transferDate").isNotEmpty())
                .andExpect(jsonPath("$.scheduledAt").isNotEmpty())
                .andExpect(jsonPath("$.fee").value(transfer.getFee()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.amount").value(transfer.getTransferAmount()));
    }


    @Test
    @WithMockUser(username = "user", password = "password")
    void shouldFindTransferById() throws Exception {

        TransferDTO transfer = mockTransfer();

        when(transferService.findById(anyLong())).thenReturn(transfer);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/transfers/{id}", 1L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(mockCreateTransferDTO()))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beneficiaryId").value(transfer.getBeneficiaryId()))
                .andExpect(jsonPath("$.transferDate").isNotEmpty())
                .andExpect(jsonPath("$.scheduledAt").isNotEmpty())
                .andExpect(jsonPath("$.fee").value(transfer.getFee()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.amount").value(transfer.getAmount()));
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    void shouldFindAllTransfer() throws Exception {

        TransferDTO transfer = mockTransfer();

        var returnList = Arrays.asList(transfer, transfer);

        when(transferService.findAll()).thenReturn(returnList);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/transfers", 1L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(mockCreateTransferDTO()))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].beneficiaryId").value(transfer.getBeneficiaryId()))
                .andExpect(jsonPath("$.[0].transferDate").isNotEmpty())
                .andExpect(jsonPath("$.[0].scheduledAt").isNotEmpty())
                .andExpect(jsonPath("$.[0].fee").value(transfer.getFee()))
                .andExpect(jsonPath("$.[0].id").isNotEmpty())
                .andExpect(jsonPath("$.[0].amount").value(transfer.getTransferAmount()))
                .andExpect(jsonPath("$.[1].beneficiaryId").value(transfer.getBeneficiaryId()))
                .andExpect(jsonPath("$.[1].transferDate").isNotEmpty())
                .andExpect(jsonPath("$.[1].scheduledAt").isNotEmpty())
                .andExpect(jsonPath("$.[1].fee").value(transfer.getFee()))
                .andExpect(jsonPath("$.[1].id").isNotEmpty())
                .andExpect(jsonPath("$.[1].amount").value(transfer.getTransferAmount()));
    }

}