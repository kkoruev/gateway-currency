package com.fixer.currencygateway.xml.dto;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "command")
public class Command {

    @XmlAttribute
    private UUID id;

    @XmlElement(name = "get")
    private GetRequestCommand get;

    @XmlElement(name = "history")
    private HistoryRequestCommand history;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GetRequestCommand getGet() {
        return get;
    }

    public void setGet(GetRequestCommand get) {
        this.get = get;
    }

    public HistoryRequestCommand getHistory() {
        return history;
    }

    public void setHistory(HistoryRequestCommand history) {
        this.history = history;
    }
}
