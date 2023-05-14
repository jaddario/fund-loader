package com.vault.fundsloaderapplication.repository;

import com.vault.fundsloaderapplication.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface FundsLoaderOperationRepository extends JpaRepository<Operation, UUID> {


    @Query(
            value = """
                        SELECT coalesce(sum(loadAmount), 0)
                          FROM Operation
                         WHERE customerId = :customerId
                           AND accepted = true
                           AND time = :date
                    """
    )
    BigDecimal dailyLoadAmountsFromCustomer(@Param("customerId") long customerId, @Param("date") LocalDateTime date);

    @Query(
            value = """
                        SELECT coalesce(sum(loadAmount), 0)
                          FROM Operation
                         WHERE customerId = :customerId
                           AND accepted = true
                           AND time between :firstDayOfWeek and :lastDayOfWeek
                    """
    )
    BigDecimal weeklyLoadAmountsFromCustomer(
            @Param("customerId") long customerId,
            @Param("firstDayOfWeek") LocalDateTime firstDayOfWeek, @Param(
            "lastDayOfWeek") LocalDateTime lastDayOfWeek);

    @Query(
            value = """
                        SELECT count(*)
                          FROM Operation
                         WHERE customerId = :customerId
                           AND accepted = true
                           AND time = :date
                    """
    )
    int dailyOperationsFromCustomer(@Param("customerId") long customerId, @Param("date") LocalDateTime date);
}
