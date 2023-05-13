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
                       select *
                         from OPERATIONS
                        where OPERATIONS.CUSTOMER_ID = :customerId
                    """,
            nativeQuery = true
    )
    List<Operation> findAllLoadRequestsFromCustomerId(@Param("customerId") long customerId);


    @Query(
            value = """
                        SELECT coalesce(sum(OPERATIONS.LOAD_AMOUNT), 0)
                          FROM OPERATIONS
                         WHERE OPERATIONS.CUSTOMER_ID = :customerId
                           AND ACCEPTED = TRUE
                           AND TIME = :date
                    """,
            nativeQuery = true
    )
    BigDecimal dailyLoadAmountsFromCustomer(@Param("customerId") long customerId, @Param("date") LocalDateTime date);
    @Query(
            value = """
                        SELECT coalesce(sum(OPERATIONS.LOAD_AMOUNT), 0)
                          FROM OPERATIONS
                         WHERE OPERATIONS.CUSTOMER_ID = :customerId
                           AND ACCEPTED = TRUE
                           AND TIME between :firstDayOfWeek and :lastDayOfWeek
                    """,
            nativeQuery = true
    )
    BigDecimal weeklyLoadAmountsFromCustomer(@Param("customerId") long customerId, @Param("firstDayOfWeek") LocalDateTime firstDayOfWeek, @Param("lastDayOfWeek") LocalDateTime lastDayOfWeek);

    @Query(
            value = """
                        SELECT count(*)
                          FROM OPERATIONS
                         WHERE OPERATIONS.CUSTOMER_ID = :customerId
                           AND ACCEPTED = TRUE
                           AND TIME = :date
                    """,
            nativeQuery = true
    )
    int dailyOperationsFromCustomer(@Param("customerId") long customerId, @Param("date") LocalDateTime date);


    @Query(
            value = """
                        select *
                          from OPERATIONS
                         where OPERATIONS.ID = :id
                    """,
            nativeQuery = true
    )
    List<Operation> operationsById(@Param("id") long id);

}
