package com.bank.bank_api.infraestructure.dynamicfeign;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ExtendedFeignClientsRegistrar.class)
public @interface EnableExtendedFeignClients {

    /**
     * Paquetes a escanear.
     */
    String[] basePackages() default {};
}
