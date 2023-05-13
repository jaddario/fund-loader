package com.vault.fundsloaderapplication.validator;

import com.vault.fundsloaderapplication.exceptions.LoadRequestException;
import com.vault.fundsloaderapplication.model.LoadRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class LoadRequestValidator implements RequestValidator<LoadRequest> {

    @Override
    public void validate(@NonNull LoadRequest loadRequest) {
        if (loadRequest.isLoadAmountNotInPattern()) {
            throw new LoadRequestException(String.format("Load amount %s is out of pattern", loadRequest.getLoadAmount()));
        }
    }
}
