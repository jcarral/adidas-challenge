package com.adidas.api.client.impl;

import com.adidas.api.client.CurrencyExchangeService;
import com.adidas.api.constants.Currency;
import com.adidas.api.dto.CurrencyError;
import com.adidas.api.dto.CurrencyExchangeDTO;
import com.adidas.api.exception.CurrencyException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FixerCurrencyExchangeServiceImpl implements CurrencyExchangeService {

    Logger logger = LoggerFactory.getLogger(FixerCurrencyExchangeServiceImpl.class);

    @Value("${api.currency.fixer.url}")
    private String baseUrl;

    @Value("${api.currency.fixer.key}")
    private String apiKey;

    private ModelMapper modelMapper;

    @Autowired
    public FixerCurrencyExchangeServiceImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public CurrencyExchangeDTO getExchange(Currency currency) throws CurrencyException{

        logger.info("Requesting currency exchange for {}", currency);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder =  UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("symbols", currency.toString())
                .queryParam("access_key", apiKey);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        JSONObject json = new JSONObject(response.getBody());
        boolean isSuccessful = json.getBoolean("success");

        if(!isSuccessful) {
            CurrencyError currencyError = modelMapper.map(json, CurrencyError.class);
            logger.error("Invalid response, {}", currencyError.getErrorMessage());
            throw new CurrencyException(currencyError.getStatusCode(), currencyError.getType());
        } else {
            logger.info("Request to obtain currency exchange for {} was successful", currency);
            return modelMapper.map(json, CurrencyExchangeDTO.class);
        }

    }

}
