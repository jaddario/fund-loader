package com.vault.fundsloaderapplication.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadRequest {
    private static final String CURRENCY_REGEX = "^\\$(0|[1-9][0-9]{0,2})(,\\d{3})*(\\.\\d{1,2})?$";
    private long id;
    @JsonAlias("customer_id")
    private long customerId;
    @JsonAlias("load_amount")
    private String loadAmount;
    private LocalDateTime time;

    public Boolean isLoadAmountNotInPattern() {
        return !loadAmount.matches(CURRENCY_REGEX);
    }
}
