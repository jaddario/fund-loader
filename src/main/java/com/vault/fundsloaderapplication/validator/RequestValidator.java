package com.vault.fundsloaderapplication.validator;


import lombok.NonNull;

@FunctionalInterface
public interface RequestValidator<T> {
    void validate(@NonNull T element);
}
