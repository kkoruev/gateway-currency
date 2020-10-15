package com.fixer.currencygateway.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fixer.currencygateway.exception.ExceptionFactory;
import com.fixer.currencygateway.model.Currency;
import com.fixer.currencygateway.model.ExchangeRate;
import com.fixer.currencygateway.model.Request;
import com.fixer.currencygateway.service.base.RequestService;
import com.fixer.currencygateway.service.base.XmlCommandService;
import com.fixer.currencygateway.xml.dto.Command;
import com.fixer.currencygateway.xml.dto.GetRequestCommand;
import com.fixer.currencygateway.xml.dto.HistoryRequestCommand;

@Service
public class XmlCommandServiceImpl implements XmlCommandService {

    private static final String SERVICE_NAME = "EXT_SERVICE_XML";

    private RequestService requestService;

    public XmlCommandServiceImpl(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public List<ExchangeRate> getExchangeRate(Command command) {
        if (isInvalidValid(command)) {
            throw ExceptionFactory.commandBadRequest();
        }

        if (command.getGet() != null) {
            return Collections.singletonList(
                    requestService.createRateRequest(toRequestFromGetXml(command.getGet(), command.getId())));
        }

        if (command.getHistory() != null) {
            return requestService.createHistoryRateRequest(toRequestFromHistoryXml(command.getHistory(), command.getId()));
        }
        return Collections.emptyList();
    }

    private boolean isInvalidValid(Command command) {
        return (command.getGet() != null && command.getHistory() != null);
    }

    private Request toRequestFromGetXml(GetRequestCommand getRequestCommand, UUID id) {
        Request request = new Request();
        request.setServiceName(SERVICE_NAME);
        Currency currency = new Currency();
        currency.setCode(getRequestCommand.getCurrency());

        request.setId(id);
        request.setCurrency(currency);
        request.setClient(getRequestCommand.getConsumer());
        request.setTimestamp(LocalDateTime.now());
        return request;
    }

    private Request toRequestFromHistoryXml(HistoryRequestCommand historyRequestCommand, UUID id) {
        Request request = new Request();
        request.setServiceName(SERVICE_NAME);
        Currency currency = new Currency();
        currency.setCode(historyRequestCommand.getCurrency());

        request.setId(id);
        request.setCurrency(currency);
        request.setClient(historyRequestCommand.getConsumer());
        request.setPeriod(historyRequestCommand.getPeriod());
        request.setTimestamp(LocalDateTime.now());
        return request;
    }
}
