package com.adidas.api.service.impl;

import com.adidas.api.cache.CurrencyExchangeCacheWrapper;
import com.adidas.api.client.CurrencyExchangeService;
import com.adidas.api.constants.Currency;
import com.adidas.api.dto.CurrencyExchangeDTO;
import com.adidas.api.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private CurrencyExchangeService exchangeService;
    private CurrencyExchangeCacheWrapper cacheWrapper;

    @Autowired
    public CurrencyServiceImpl(CurrencyExchangeService exchangeService, CurrencyExchangeCacheWrapper cacheWrapper){
        this.exchangeService = exchangeService;
        this.cacheWrapper = cacheWrapper;
    }


    @Override
    public Double convertToEur(Double price, Currency currency) {

        logger.info("Converting {} from {} to EUR", price, currency.toString());

        Optional<CurrencyExchangeDTO> optional = cacheWrapper.getCurrencyExchange(currency.toString());
        CurrencyExchangeDTO exchangeDTO = null;

        if(optional.isPresent()) {
            logger.info("Currency exchange for {} is already cached", currency.toString());
            exchangeDTO  = optional.get();
        } else {
            logger.info("Currency exchange for {} is not cached.", currency.toString());
            exchangeDTO = exchangeService.getExchange(currency);

            cacheWrapper.setCurrencyExchange(exchangeDTO);
        }

        return price / exchangeDTO.getExchange();
    }
}
