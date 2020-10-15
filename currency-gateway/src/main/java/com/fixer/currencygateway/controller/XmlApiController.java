package com.fixer.currencygateway.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fixer.currencygateway.model.ExchangeRate;
import com.fixer.currencygateway.service.base.XmlCommandService;
import com.fixer.currencygateway.xml.dto.Command;

@RestController
@RequestMapping("xml-api")
public class XmlApiController {

    private XmlCommandService xmlCommandService;

    public XmlApiController(XmlCommandService xmlCommandService) {
        this.xmlCommandService = xmlCommandService;
    }

    @PostMapping(consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<List<ExchangeRate>> createCurrentRateRequest(@RequestBody Command command) {
        return new ResponseEntity<>(xmlCommandService.getExchangeRate(command), HttpStatus.CREATED);
    }
}
