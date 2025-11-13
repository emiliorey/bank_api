package com.bank.bank_api.infraestructure.dynamicfeign;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicFeignClient {
    String name();
    String defaultUrl();
}
