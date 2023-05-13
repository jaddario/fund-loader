package com.vault.fundsloaderapplication.service;

import com.vault.fundsloaderapplication.mapping.LoadMapper;
import com.vault.fundsloaderapplication.model.LoadRequest;
import com.vault.fundsloaderapplication.model.LoadResponse;
import com.vault.fundsloaderapplication.model.Operation;
import com.vault.fundsloaderapplication.repository.FundsLoaderOperationRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FundsLoaderServiceTest {

    @Mock
    private FundsLoaderOperationRepository repository;
    @Mock
    private LoadMapper mapper;
    @InjectMocks
    private FundsLoaderService service;


    @Test
    void loadFunds_isSuccessful() {
        LoadRequest request = createRequest();

        LoadResponse expectedResponse = getExpectedResponse(true);

        Operation operation = getOperation();

        List<Operation> operations = List.of(operation);

        when(repository.dailyOperationsFromCustomer(anyLong(), any())).thenReturn(1);
        when(repository.weeklyLoadAmountsFromCustomer(anyLong(), any(), any())).thenReturn(BigDecimal.valueOf(250.45));
        when(repository.dailyLoadAmountsFromCustomer(anyLong(), any())).thenReturn(BigDecimal.valueOf(250.45));

        when(mapper.toOperation(any())).thenReturn(operation);
        when(mapper.toLoadResponse(any())).thenReturn(expectedResponse);

        LoadResponse actualResponse = service.loadFunds(request);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @Disabled
    void loadFunds_tryToLoad3Times_isSuccessfulButAcceptedFalse() {
        LoadRequest request = createRequest();
        LoadResponse expectedResponse = getExpectedResponse(false);

        Operation operation = getOperation();

        List<Operation> operations = List.of(operation);

        when(mapper.toOperation(any())).thenReturn(operation);

        when(repository.weeklyLoadAmountsFromCustomer(anyLong(), any(), any())).thenReturn(BigDecimal.valueOf(250.45));
        when(repository.dailyLoadAmountsFromCustomer(anyLong(), any())).thenReturn(BigDecimal.valueOf(250.45));

        when(repository.dailyOperationsFromCustomer(anyLong(), any())).thenReturn(3);

        LoadResponse actualResponse = service.loadFunds(request);

        assertFalse(actualResponse.isAccepted());
        verify(repository).save(any());
    }

    private static Operation getOperation() {
        return Operation.builder()
                .uuid(UUID.randomUUID())
                .id(123)
                .customerId(1234)
                .loadAmount(BigDecimal.valueOf(250.45))
                .time(LocalDateTime.now())
                .build();
    }

    private static LoadResponse getExpectedResponse(boolean accepted) {
        return LoadResponse.builder()
                .id(123)
                .customerId(1234)
                .accepted(accepted)
                .build();
    }

    private static LoadRequest createRequest() {
        return LoadRequest.builder()
                .id(123)
                .customerId(1234)
                .loadAmount("$250.45")
                .time(LocalDateTime.now())
                .build();
    }


}