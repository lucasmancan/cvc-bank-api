package br.com.app.controllers.v1;

import br.com.app.dtos.AccountDTO;
import br.com.app.entities.AccountType;
import br.com.app.exceptions.ValidationException;
import br.com.app.services.AccountService;
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

    public AccountDTO mockValidAccount() {
        AccountDTO dto = new AccountDTO();
        dto.setDocument("88741439074");
        dto.setType(AccountType.individual);
        dto.setPassword("123456");
        return dto;
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    void shouldCreateNewAccountWithValidInfo() throws Exception {

        var dto = mockValidAccount();

        var createdAccount = new AccountDTO();
        createdAccount.setId(1L);
        createdAccount.setDocument(dto.getDocument());
        createdAccount.setType(dto.getType());
        createdAccount.setNumber("12323");
        createdAccount.setUpdatedAt(LocalDateTime.now());

        when(accountService.create(any(AccountDTO.class))).thenReturn(createdAccount);

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
    void shouldCreateNewAccountWithValidCNPJInfo() throws Exception {
        AccountDTO dto = new AccountDTO();

        dto.setDocument("191.704.39/0001-90");
        dto.setType(AccountType.legal);
        dto.setPassword("123456");

        var createdAccount = new AccountDTO();
        createdAccount.setId(1L);
        createdAccount.setDocument(dto.getDocument());
        createdAccount.setType(dto.getType());
        createdAccount.setNumber("12323");
        createdAccount.setUpdatedAt(LocalDateTime.now());

        when(accountService.create(any(AccountDTO.class))).thenReturn(createdAccount);

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

        when(accountService.create(any(AccountDTO.class))).thenThrow(ValidationException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/accounts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto))).andExpect(status().isBadRequest());

    }
}