package com.vault.fundsloaderapplication.mapping;

import com.vault.fundsloaderapplication.model.LoadRequest;
import com.vault.fundsloaderapplication.model.LoadResponse;
import com.vault.fundsloaderapplication.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface LoadMapper {

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "accepted", ignore = true)
    @Mapping(target = "loadAmount", expression = "java(getLoadAmountInBigDecimal(request.getLoadAmount()))")
    @Mapping(target = "customerId", source = "customerId")
    Operation toOperation(LoadRequest request);

    LoadResponse toLoadResponse(Operation operation);

    default BigDecimal getLoadAmountInBigDecimal(String loadAmount) {
        var sanitizedLoadAmount = loadAmount.substring(1).replaceAll(",", "");
        return new BigDecimal(sanitizedLoadAmount);
    }
}
