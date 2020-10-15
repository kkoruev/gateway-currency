package com.fixer.currencygateway.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fixer.currencygateway.exception.ExceptionFactory;
import com.fixer.currencygateway.model.Currency;
import com.fixer.currencygateway.model.ExchangeRate;
import com.fixer.currencygateway.model.Request;
import com.fixer.currencygateway.repository.CurrencyRepository;
import com.fixer.currencygateway.repository.ExchangeRateRepository;
import com.fixer.currencygateway.repository.RequestRepository;
import com.fixer.currencygateway.service.base.RequestService;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;
    private CurrencyRepository currencyRepository;
    private ExchangeRateRepository exchangeRateRepository;

    public RequestServiceImpl(RequestRepository requestRepository,
            CurrencyRepository currencyRepository,
            ExchangeRateRepository exchangeRateRepository) {
        this.requestRepository = requestRepository;
        this.currencyRepository = currencyRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public ExchangeRate createRateRequest(Request request) {
        handleInitialRequestCheck(request);
        ExchangeRate exchangeRate =  exchangeRateRepository
                .findFirstByCurrencyOrderByTimestampDesc(request.getCurrency())
                .orElseThrow(ExceptionFactory::exchangeRateNotFound);
        requestRepository.save(request);
        return exchangeRate;
    }

    @Override
    public List<ExchangeRate> createHistoryRateRequest(Request request) {
        handleInitialRequestCheck(request);
        List<ExchangeRate> exchangeRates = exchangeRateRepository
                .findAllByCurrencyAndTimestampBetween(request.getCurrency(), getTimeBefore(request.getPeriod()),
                        LocalDateTime.now());
        requestRepository.save(request);
        return exchangeRates;
    }

    private void handleInitialRequestCheck(Request request) {
        Optional<Request> optionalRequest = requestRepository.findById(request.getId());
        if (optionalRequest.isPresent()) {
            throw ExceptionFactory.requestAlreadyExists();
        }
        Currency currency = currencyRepository
                .findByCode(request.getCurrency().getCode()).orElseThrow(ExceptionFactory::currencyNotFound);
        request.setCurrency(currency);
    }

    private LocalDateTime getTimeBefore(int hours) {
        return LocalDateTime.now().minusHours(hours);
    }
}
