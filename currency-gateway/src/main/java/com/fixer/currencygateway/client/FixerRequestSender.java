package com.fixer.currencygateway.client;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fixer.currencygateway.exception.ExceptionFactory;
import com.fixer.currencygateway.json.fixer.RateResponse;
import com.fixer.currencygateway.json.fixer.Symbol;
import com.fixer.currencygateway.model.Currency;
import com.fixer.currencygateway.model.ExchangeRate;

@Component
public class FixerRequestSender {

    private final static String REQUEST_URL_FORMAT = "%s/%s?access_key=%s";  //basicUrl/endpoint?access_key=apiKey
    private final static String LATEST_ENDPOINT = "latest";
    private final static String ALL_CURRENCIES_ENDPOINT = "symbols";

    private final String basicUrl;
    private final String apiKey;

    private final RestTemplate restTemplate;

    public FixerRequestSender(@Value("${currency.gateway.fixer.base.url}") String basicUrl,
            @Value("${currency.gateway.fixer.api.key}")String apiKey, RestTemplate restTemplate) {
        this.basicUrl = basicUrl;
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    public Set<String> getAllAvailableCurrencyCodes() {
        String url = String.format(REQUEST_URL_FORMAT, basicUrl, ALL_CURRENCIES_ENDPOINT, apiKey);
        ResponseEntity<Symbol> responseEntity = restTemplate.getForEntity(url, Symbol.class);
        handleNullResponseBody(responseEntity);
        return responseEntity.getBody().getSymbols().keySet();
    }

    public RateResponse getLatestExchangeRates() {
        String url = String.format(REQUEST_URL_FORMAT, basicUrl, LATEST_ENDPOINT, apiKey);
        ResponseEntity<RateResponse> responseEntity = restTemplate.getForEntity(url, RateResponse.class);
        handleNullResponseBody(responseEntity);
        return responseEntity.getBody();
    }

    private <T> void handleNullResponseBody(ResponseEntity<T> responseEntity) {
        if (responseEntity == null || responseEntity.getBody() == null) {
            throw ExceptionFactory.fixerNotResponding();
        }
    }
}
