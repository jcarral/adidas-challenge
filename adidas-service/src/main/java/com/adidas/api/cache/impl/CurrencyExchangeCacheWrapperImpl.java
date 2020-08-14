package com.adidas.api.cache.impl;

import com.adidas.api.cache.CurrencyExchangeCacheWrapper;
import com.adidas.api.constants.CacheConstants;
import com.adidas.api.dto.CurrencyExchangeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyExchangeCacheWrapperImpl implements CurrencyExchangeCacheWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyExchangeCacheWrapperImpl.class);

    @Override
    @Cacheable(value = CacheConstants.CURRENCY_CACHE,
            key = "#currency", unless = "#result == null")
    public Optional<CurrencyExchangeDTO> getCurrencyExchange(String currency) {
        LOGGER.info("{} currency not cached", currency);

        return null;
    }

    @Override
    @CachePut(value = CacheConstants.CURRENCY_CACHE,
            key = "#currencyExchange.currency")
    public CurrencyExchangeDTO setCurrencyExchange(CurrencyExchangeDTO currencyExchange) {
        LOGGER.info("Updating {} currency with value {}", currencyExchange.getCurrency(), currencyExchange.getExchange());

        return currencyExchange;
    }
}
