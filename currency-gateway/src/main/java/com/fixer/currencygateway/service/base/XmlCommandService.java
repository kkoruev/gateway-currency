package com.fixer.currencygateway.service.base;

import java.util.List;

import com.fixer.currencygateway.model.ExchangeRate;
import com.fixer.currencygateway.xml.dto.Command;

public interface XmlCommandService {

    List<ExchangeRate> getExchangeRate(Command command);
}
