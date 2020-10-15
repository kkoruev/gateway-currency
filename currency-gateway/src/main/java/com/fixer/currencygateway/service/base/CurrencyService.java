package com.fixer.currencygateway.service.base;

import java.util.List;

import com.fixer.currencygateway.model.Currency;

public interface CurrencyService {
    List<Currency> updateAllCurrencyCodes();
    void updateExchangeRates();
}
