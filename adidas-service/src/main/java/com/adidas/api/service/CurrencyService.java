package com.adidas.api.service;

import com.adidas.api.constants.Currency;
import com.adidas.api.exception.CurrencyException;

public interface CurrencyService {

    Double convertToEur(Double price, Currency currency) throws CurrencyException;

}
