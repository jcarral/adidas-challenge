package com.adidas.api.service;

import com.adidas.api.cache.CurrencyExchangeCacheWrapper;
import com.adidas.api.client.CurrencyExchangeService;
import com.adidas.api.config.TestConfig;
import com.adidas.api.constants.Currency;
import com.adidas.api.dto.CurrencyExchangeDTO;
import com.adidas.api.service.impl.CurrencyServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.text.DecimalFormat;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { CurrencyServiceImpl.class, TestConfig.class })
public class TestCurrencyService {

    @Mock
    private CurrencyExchangeCacheWrapper currencyExchangeCacheWrapper;
    @Mock
    private CurrencyExchangeService currencyExchangeService;

    private CurrencyService currencyService;

    @Before
    public void setUp() throws Exception {
        currencyService = new CurrencyServiceImpl(currencyExchangeService, currencyExchangeCacheWrapper);
    }

    @Test
    public void testUSDConversion() throws Exception {
        DecimalFormat df = new DecimalFormat("####0.00");
        String expectedResult = "0.85";
        Mockito.when(currencyExchangeCacheWrapper.getCurrencyExchange("USD")).thenReturn(Optional.empty());
        Mockito.when(currencyExchangeService.getExchange(Currency.USD)).thenReturn(new CurrencyExchangeDTO(Currency.EUR.toString(), Currency.USD.toString(), 1.3));

        Double calculatedValue = currencyService.convertToEur(1.1, Currency.USD);
        Assert.assertTrue(expectedResult.equals(df.format(calculatedValue)));
    }

    @Test
    public void testEURConversion() throws Exception {

        Mockito.when(currencyExchangeCacheWrapper.getCurrencyExchange("EUR")).thenReturn(Optional.empty());
        Mockito.when(currencyExchangeService.getExchange(Currency.EUR)).thenReturn(new CurrencyExchangeDTO(Currency.EUR.toString(), Currency.USD.toString(), 1D));

        Double calculatedValue = currencyService.convertToEur(1.3, Currency.EUR);
        Assert.assertTrue(calculatedValue.equals(1.3));
    }


}
