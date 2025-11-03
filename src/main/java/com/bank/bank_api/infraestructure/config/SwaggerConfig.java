package com.bank.bank_api.infraestructure.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class SwaggerConfig {

	@Value("${app.version:1.0.1}")
    private String appVersion;
	
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Bank Api")
                        .description("""
                                Microservices to main functionalities for a bank
                                """)
                        .version("1.01")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
