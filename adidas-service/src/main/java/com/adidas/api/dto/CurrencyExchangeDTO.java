package com.adidas.api.dto;

public class CurrencyExchangeDTO {

    private String base;
    private String currency;
    private Double exchange;

    public CurrencyExchangeDTO(String base, String currency, Double exchange) {
        this.base = base;
        this.currency = currency;
        this.exchange = exchange;
    }

    public CurrencyExchangeDTO() {
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getExchange() {
        return exchange;
    }

    public void setExchange(Double exchange) {
        this.exchange = exchange;
    }
}
