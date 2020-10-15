package com.fixer.currencygateway.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fixer.currencygateway.service.base.CurrencyService;

@Component
public class CurrencyRatesRetriever {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRatesRetriever.class);

    private CurrencyService currencyService;

    public CurrencyRatesRetriever(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Scheduled(fixedRateString = "${currency.gateway.fixer.updater.currency.interval}")
    public void updateCurrencyCodes() {
        LOGGER.info("Updating currency codes");
        currencyService.updateAllCurrencyCodes();
    }

    @Scheduled(fixedRateString = "${currency.gateway.fixer.update.rates.interval}")
    public void retrieveCurrencyRates() {
        currencyService.updateExchangeRates();
    }
}
