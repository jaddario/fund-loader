package com.vault.fundsloaderapplication.controller;

import com.vault.fundsloaderapplication.model.LoadRequest;
import com.vault.fundsloaderapplication.model.LoadResponse;
import com.vault.fundsloaderapplication.service.FundsLoaderService;
import com.vault.fundsloaderapplication.validator.LoadRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class FundsLoaderController {
    private final FundsLoaderService fundsLoaderService;
    private final LoadRequestValidator loadRequestValidator;

    public FundsLoaderController(FundsLoaderService fundsLoaderService, LoadRequestValidator loadRequestValidator) {
        this.fundsLoaderService = fundsLoaderService;
        this.loadRequestValidator = loadRequestValidator;
    }

    @PostMapping("/load-funds")
    public ResponseEntity<LoadResponse> loadFunds(@RequestBody LoadRequest loadRequest) {
        loadRequestValidator.validate(loadRequest);
        LoadResponse loadResponse = fundsLoaderService.loadFunds(loadRequest);
        return new ResponseEntity<>(loadResponse, HttpStatus.OK);
    }
}