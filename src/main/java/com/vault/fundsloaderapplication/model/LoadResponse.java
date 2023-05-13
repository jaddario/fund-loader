package com.vault.fundsloaderapplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadResponse {
    private long id;
    private long customerId;
    private boolean accepted;
}
