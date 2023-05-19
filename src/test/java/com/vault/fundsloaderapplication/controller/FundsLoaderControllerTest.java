package com.vault.fundsloaderapplication.controller;

import com.vault.fundsloaderapplication.model.LoadRequest;
import com.vault.fundsloaderapplication.model.Operation;
import com.vault.fundsloaderapplication.repository.FundsLoaderOperationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class FundsLoaderControllerTest {

    @Autowired
    private FundsLoaderOperationRepository repository;

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Container
    private static final MySQLContainer container = new MySQLContainer<>(DockerImageName.parse("mysql:5.7"));

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("TRUNCATE TABLE operations");
    }

    @Test
    void loadFunds_isSuccessful() throws Exception {
        var request = """
                {
                  "id": "123",
                  "customer_id": "1234",
                  "load_amount": "$123.45",
                  "time": "2018-01-01T00:00:00Z"
                }
                """;

        mockMvc.perform(post("/v1/api/load-funds")
                        .content(request)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.customerId").value("1234"))
                .andExpect(jsonPath("$.accepted").value(true));
    }

    @Test
    void loadFunds_triesToAddMoreThan5KInADay_returnsAcceptedFalse() throws Exception {
        var operation = createOperation(4999, LocalDateTime.of(2023, 5, 19, 17, 30, 0));

        repository.save(operation);

        var request = """
                {
                  "id": "123",
                  "customer_id": "1234",
                  "load_amount": "$123.45",
                  "time": "2023-05-19T17:32:00Z"
                }
                """;

        mockMvc.perform(post("/v1/api/load-funds")
                        .content(request)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.customerId").value("1234"))
                .andExpect(jsonPath("$.accepted").value(false));
    }

    private Operation createOperation(long value, LocalDateTime time) {
        return Operation.builder()
                .id(123)
                .customerId(1234)
                .loadAmount(BigDecimal.valueOf(value))
                .time(time)
                .accepted(true)
                .build();
    }
}