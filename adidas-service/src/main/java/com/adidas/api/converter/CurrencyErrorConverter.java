package com.adidas.api.converter;

import com.adidas.api.dto.CurrencyError;
import org.json.JSONObject;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrencyErrorConverter implements Converter<JSONObject, CurrencyError> {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyErrorConverter.class);

    @Override
    public CurrencyError convert(MappingContext<JSONObject, CurrencyError> mappingContext) {

        JSONObject source = mappingContext.getSource();
        CurrencyError dest = new CurrencyError();

        JSONObject json = source.getJSONObject("error");
        dest.setStatusCode(json.getInt("code"));
        dest.setErrorMessage(json.getString("info"));
        dest.setType(json.getString("type"));

        return dest;
    }
}
