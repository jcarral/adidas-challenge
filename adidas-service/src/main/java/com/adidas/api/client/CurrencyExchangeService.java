package com.adidas.api.client;

import com.adidas.api.constants.Currency;
import com.adidas.api.dto.CurrencyExchangeDTO;

public interface CurrencyExchangeService {

    CurrencyExchangeDTO getExchange(Currency currency);

}
