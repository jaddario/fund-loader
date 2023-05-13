package com.vault.fundsloaderapplication.service;

import com.vault.fundsloaderapplication.mapping.LoadMapper;
import com.vault.fundsloaderapplication.model.LoadRequest;
import com.vault.fundsloaderapplication.model.LoadResponse;
import com.vault.fundsloaderapplication.model.Operation;
import com.vault.fundsloaderapplication.repository.FundsLoaderOperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FundsLoaderService {
    private static final int DAILY_OPERATIONS_LIMIT = 3;
    private static final BigDecimal DAILY_AMOUNT_LIMIT = BigDecimal.valueOf(5000);
    private static final BigDecimal WEEKLY_AMOUNT_LIMIT = BigDecimal.valueOf(20000);

    private final FundsLoaderOperationRepository fundsLoaderOperationRepository;
    private final LoadMapper mapper;

    public FundsLoaderService(FundsLoaderOperationRepository fundsLoaderOperationRepository, LoadMapper mapper) {
        this.fundsLoaderOperationRepository = fundsLoaderOperationRepository;
        this.mapper = mapper;
    }

    public LoadResponse loadFunds(LoadRequest loadRequest) {

        Operation operation = mapper.toOperation(loadRequest);
        boolean accepted = this.isOperationAccepted(operation);
        operation.setAccepted(accepted);
        fundsLoaderOperationRepository.save(operation);

        return mapper.toLoadResponse(operation);
    }

    private boolean isOperationAccepted(Operation operation) {
        if (maximumLoadsIsReachedPer(operation)) {
            return false;
        }

        if (loadAmountExceedsDailyLimitsPer(operation)) {
            return false;
        }

        if (loadAmountExceedsWeeklyLimitPer(operation))
            return false;

        return true;
    }

    private boolean loadAmountExceedsWeeklyLimitPer(Operation operation) {
        LocalDateTime operationDate = operation.getTime();
        LocalDateTime firstDayOfWeek = operationDate.with(DayOfWeek.MONDAY);
        LocalDateTime lastDayOfWeek = operationDate.with(DayOfWeek.SUNDAY);

        BigDecimal totalAmountPerWeek =
                this.fundsLoaderOperationRepository.weeklyLoadAmountsFromCustomer(operation.getCustomerId(),
                        firstDayOfWeek, lastDayOfWeek);

        return operation.getLoadAmount().add(totalAmountPerWeek).compareTo(WEEKLY_AMOUNT_LIMIT) > 0;
    }

    private boolean maximumLoadsIsReachedPer(Operation operation) {
        int dailyOperations = this.fundsLoaderOperationRepository
                .dailyOperationsFromCustomer(operation.getCustomerId(), operation.getTime());
        return dailyOperations >= DAILY_OPERATIONS_LIMIT;
    }

    private boolean loadAmountExceedsDailyLimitsPer(Operation operation) {
        BigDecimal totalAmountPerDay = this.fundsLoaderOperationRepository
                .dailyLoadAmountsFromCustomer(operation.getCustomerId(), operation.getTime());
        return operation.getLoadAmount().add(totalAmountPerDay).compareTo(DAILY_AMOUNT_LIMIT) > 0;
    }
}