package com.adidas.api;

import com.adidas.api.config.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ ServiceConfig.class })
public class App 
{
    public static void main(String [] args){
        SpringApplication.run(App.class, args);
    }
}
