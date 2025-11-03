package com.bank.bank_api.infraestructure.config;

import com.bank.bank_api.infraestructure.repositories.externalservices.BankFeignClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = {
        BankFeignClient.class,
})
public class FeignClientsConfig extends FeignClientsConfiguration {
}
