package com.adidas.api.config;


import com.adidas.api.cache.impl.CacheMarker;
import com.adidas.api.client.impl.ClientMarker;
import com.adidas.api.controller.ControllerMarker;
import com.adidas.api.converter.CurrencyErrorConverter;
import com.adidas.api.converter.CurrencyExchangeConverter;
import com.adidas.api.exception.ExceptionMarker;
import com.adidas.api.persistence.model.EntityMarker;
import com.adidas.api.persistence.repository.RepositoryMarker;
import com.adidas.api.service.impl.ServiceMarker;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackageClasses = { ControllerMarker.class, ServiceMarker.class, RepositoryMarker.class, ClientMarker.class, ExceptionMarker.class, CacheMarker.class})
@EntityScan(basePackageClasses = { EntityMarker.class })
@EnableJpaRepositories(basePackageClasses = { RepositoryMarker.class })
@Import({ CacheConfig.class, SecurityConfig.class })
public class ServiceConfig {

    @Bean(name = "com.adidas.modelMapper")
    @Primary
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.addConverter(new CurrencyExchangeConverter());
        mapper.addConverter(new CurrencyErrorConverter());

        return mapper;
    }

}
