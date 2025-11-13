package com.bank.bank_api.infraestructure.dynamicfeign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicFeignClientConfiguration {

    @Bean
    public ExtendedFeignClientsRegistrar extendedFeignClientsRegistrar(){
        return new ExtendedFeignClientsRegistrar();
    }
}
