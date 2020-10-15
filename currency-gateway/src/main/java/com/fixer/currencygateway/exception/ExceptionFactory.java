package com.fixer.currencygateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExceptionFactory {

    private ExceptionFactory() {

    }

    public static ResponseStatusException requestAlreadyExists() {
        return new ResponseStatusException(HttpStatus.CONFLICT, "Request with the same ID already exists");
    }

    public static ResponseStatusException currencyNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Provided currency is not supported in the Gateway");
    }

    public static ResponseStatusException exchangeRateNotFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "There were no rates for the provided currency");
    }

    public static RuntimeException fixerNotResponding() {
        return new RuntimeException("Issues communicating with the Fixer API!");
    }

    public static ResponseStatusException commandBadRequest() {
        return new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Can not proccess two commands at the same time. Send only one!");
    }
}
