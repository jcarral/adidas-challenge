package com.adidas.api.converter;

import com.adidas.api.dto.CurrencyExchangeDTO;
import com.sun.org.apache.xalan.internal.xsltc.dom.KeyIndex;
import org.json.JSONObject;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

public class CurrencyExchangeConverter implements Converter<JSONObject, CurrencyExchangeDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyExchangeConverter.class);

    @Override
    public CurrencyExchangeDTO convert(MappingContext<JSONObject, CurrencyExchangeDTO> mappingContext) {

        JSONObject source = mappingContext.getSource();
        CurrencyExchangeDTO dest = new CurrencyExchangeDTO();

        JSONObject rates = source.getJSONObject("rates");
        Map.Entry<String, Object> firstRate = rates.toMap().entrySet().iterator().next();

        dest.setBase(source.getString("base"));
        dest.setCurrency(firstRate.getKey());
        dest.setExchange((Double) firstRate.getValue());

        return dest;
    }
}
