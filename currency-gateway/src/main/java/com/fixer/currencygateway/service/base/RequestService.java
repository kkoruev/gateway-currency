package com.fixer.currencygateway.service.base;

import java.util.List;

import com.fixer.currencygateway.model.ExchangeRate;
import com.fixer.currencygateway.model.Request;

public interface RequestService {

    ExchangeRate createRateRequest(Request request);
    List<ExchangeRate> createHistoryRateRequest(Request request);
}
