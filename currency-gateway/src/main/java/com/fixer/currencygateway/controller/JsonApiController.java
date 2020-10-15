package com.fixer.currencygateway.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fixer.currencygateway.model.ExchangeRate;
import com.fixer.currencygateway.model.Request;
import com.fixer.currencygateway.service.base.RequestService;

@RestController
@RequestMapping("json-api")
public class JsonApiController {

    private static final String SERVICE_NAME = "EXT_SERVICE_JSON";

    private RequestService requestService;

    public JsonApiController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/current")
    public ResponseEntity<ExchangeRate> createCurrentRateRequest(@RequestBody Request request) {
        request.setServiceName(SERVICE_NAME);
        return new ResponseEntity<>(requestService.createRateRequest(request), HttpStatus.CREATED);
    }

    @PostMapping("/history")
    public ResponseEntity<List<ExchangeRate>> createHistoryRateRequest(@RequestBody Request request) {
        request.setServiceName(SERVICE_NAME);
        return new ResponseEntity<>(requestService.createHistoryRateRequest(request), HttpStatus.CREATED);
    }
}
