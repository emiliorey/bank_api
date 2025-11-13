package com.bank.bank_api.infraestructure.dynamicfeign;

import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

public class FeignDynamicFactory extends FeignClientsConfiguration {

    public static <T> T createClient(Class<T> clazz, String baseUrl) {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .contract(new SpringMvcContract())
                .target(clazz, baseUrl);
    }
}
