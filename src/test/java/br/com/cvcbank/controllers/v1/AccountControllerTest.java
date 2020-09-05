package br.com.cvcbank.controllers.v1;

import br.com.cvcbank.dtos.AccountDTO;
import br.com.cvcbank.dtos.CreateAccountDTO;
import br.com.cvcbank.entities.AccountType;
import br.com.cvcbank.exceptions.ValidationException;
import br.com.cvcbank.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(locations = "classpath:/test/application-test.properties")
@SpringBootTest
class AccountControllerTest {

    private static ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    AccountService accountService;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach()
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    private AccountDTO mockAccountDTO() {
        AccountDTO account = new AccountDTO();
        account.setPassword("12312321");
        account.setNumber("112123");
        account.setDocument("12312312321");
        account.setType(AccountType.individual);
        account.setBalance(new BigDecimal(10000));
        account.setId(1L);
        account.setUpdatedAt(LocalDateTime.now());
        return account;
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    void shouldCreateNewAccountWithValidInfo() throws Exception {

        var createdAccount = new CreateAccountDTO();
        createdAccount.setDocument("88741439074");
        createdAccount.setType(AccountType.individual);
        createdAccount.setPassword("123456");
        createdAccount.setInitialBalance(new BigDecimal(1000));

        var dto = mockAccountDTO();

        when(accountService.create(any(CreateAccountDTO.class))).thenReturn(mockAccountDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/accounts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createdAccount))).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.document").value(dto.getDocument()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.type").value(dto.getType().toString()))
                .andExpect(jsonPath("$.number").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    void shouldCreateNewAccountWithValidCNPJInfo() throws Exception {
        CreateAccountDTO dto = new CreateAccountDTO();

        dto.setDocument("191.704.39/0001-90");
        dto.setType(AccountType.legal);
        dto.setPassword("123456");
        dto.setInitialBalance(new BigDecimal(1000));

        var createdAccount = new AccountDTO();
        createdAccount.setId(1L);
        createdAccount.setDocument(dto.getDocument());
        createdAccount.setType(dto.getType());
        createdAccount.setNumber("12323");
        createdAccount.setUpdatedAt(LocalDateTime.now());

        when(accountService.create(any(CreateAccountDTO.class))).thenReturn(createdAccount);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/accounts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto))).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.document").value(dto.getDocument()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.type").value(dto.getType().toString()))
                .andExpect(jsonPath("$.number").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    void shouldReturnErrorMessageWhenValidationExceptionOccurs() throws Exception {
        AccountDTO dto = new AccountDTO();
        dto.setDocument("12323123212");
        dto.setType(AccountType.individual);
        dto.setPassword("123456");

        when(accountService.create(any(CreateAccountDTO.class))).thenThrow(ValidationException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/accounts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().isBadRequest());

    }
}