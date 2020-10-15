package com.fixer.currencygateway.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fixer.currencygateway.client.FixerRequestSender;
import com.fixer.currencygateway.json.fixer.RateResponse;
import com.fixer.currencygateway.model.Currency;
import com.fixer.currencygateway.model.ExchangeRate;
import com.fixer.currencygateway.repository.CurrencyRepository;
import com.fixer.currencygateway.repository.ExchangeRateRepository;
import com.fixer.currencygateway.service.base.CurrencyService;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private FixerRequestSender fixerRequestSender;
    private CurrencyRepository currencyRepository;
    private ExchangeRateRepository exchangeRateRepository;

    public CurrencyServiceImpl(FixerRequestSender fixerRequestSender,
            CurrencyRepository currencyRepository,
            ExchangeRateRepository exchangeRateRepository) {
        this.fixerRequestSender = fixerRequestSender;
        this.currencyRepository = currencyRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public List<Currency> updateAllCurrencyCodes() {
        List<Currency> savedCurrencies = currencyRepository.findAll();
        Set<String> currencyCodes = fixerRequestSender.getAllAvailableCurrencyCodes();
        List<Currency> newCurrencies = filterNewCurrencies(savedCurrencies, currencyCodes);
        if (newCurrencies.isEmpty()) {
            return Collections.emptyList();
        }
        return currencyRepository.saveAll(newCurrencies);
    }

    @Override
    public void updateExchangeRates() {
        Map<String, Currency> currencies = new HashMap<>();
        currencyRepository.findAll().forEach(currency -> currencies.put(currency.getCode(), currency));
        RateResponse rateResponse = fixerRequestSender.getLatestExchangeRates();
        Currency base = currencies.get(rateResponse.getBase());

        List<ExchangeRate> exchangeRates = rateResponse.getRates().keySet().stream()
                .filter(currencies::containsKey)
                .map(key -> this.toExchangeRate(base, currencies.get(key), rateResponse.getRates().get(key)))
                .collect(Collectors.toList());
        exchangeRateRepository.saveAll(exchangeRates);
    }

    private List<Currency> filterNewCurrencies(List<Currency> savedCurrencies, Set<String> currencyCodes) {
        Set<String> savedCurrencyCodes = savedCurrencies.stream().map(Currency::getCode).collect(Collectors.toSet());
        return currencyCodes.stream()
                .filter(currencyCode -> !savedCurrencyCodes.contains(currencyCode))
                .map(this::toCurrency)
                .collect(Collectors.toList());
    }

    private Currency toCurrency(String currencyCode) {
        Currency currency = new Currency();
        currency.setCode(currencyCode);
        return currency;
    }

    private ExchangeRate toExchangeRate(Currency base, Currency currency, Double rate) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(base);
        exchangeRate.setCurrency(currency);
        exchangeRate.setTimestamp(LocalDateTime.now());
        exchangeRate.setRate(rate);
        return exchangeRate;
    }
}
