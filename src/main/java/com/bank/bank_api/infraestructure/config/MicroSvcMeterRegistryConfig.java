package com.bank.bank_api.infraestructure.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicroSvcMeterRegistryConfig {

    @Value("${spring.application.name}")
    String appName;

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configureMetricsRegistry()
    {
        return registry -> registry.config().commonTags(
                "application", appName
        );
    }

}
