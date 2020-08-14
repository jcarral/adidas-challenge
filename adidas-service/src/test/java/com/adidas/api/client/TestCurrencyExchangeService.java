package com.adidas.api.client;

import com.adidas.api.client.impl.FixerCurrencyExchangeServiceImpl;
import com.adidas.api.config.TestConfig;
import com.adidas.api.constants.Currency;
import com.adidas.api.dto.CurrencyExchangeDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { FixerCurrencyExchangeServiceImpl.class, TestConfig.class })
public class TestCurrencyExchangeService {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    private ModelMapper modelMapper;

    public TestCurrencyExchangeService(){
        System.setProperty("api.currency.fixer.url", "http://data.fixer.io/api/latest");
        System.setProperty("api.currency.fixer.key", "c8cd43157856508736d1ef57eee58643");
    }

    @Test
    public void testUSDExchangeCurrency() throws Exception {
        CurrencyExchangeDTO currencyExchangeDTO = currencyExchangeService.getExchange(Currency.USD);
        Assert.notNull(currencyExchangeDTO);
    }


}
