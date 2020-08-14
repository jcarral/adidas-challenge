package com.adidas.api.cache;

import com.adidas.api.dto.CurrencyExchangeDTO;

import java.util.Optional;

public interface CurrencyExchangeCacheWrapper {

    Optional<CurrencyExchangeDTO> getCurrencyExchange(String currency);
    CurrencyExchangeDTO setCurrencyExchange(CurrencyExchangeDTO currencyExchange);
}
