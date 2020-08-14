package com.adidas.api.config;

import com.adidas.api.converter.CurrencyErrorConverter;
import com.adidas.api.converter.CurrencyExchangeConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean(name = "com.adidas.test.modelMapper")
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addConverter(new CurrencyExchangeConverter());
        mapper.addConverter(new CurrencyErrorConverter());

        return mapper;
    }
}
