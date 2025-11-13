package com.bank.bank_api.infraestructure.dynamicfeign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@DynamicFeignClient(
        name = "jsonPlaceholderClient",
        defaultUrl = "https://jsonplaceholder.typicode.com"
)
public interface ExternalApiClient {

    @GetMapping("/posts/{id}")
    Map<String, String> getPost(@PathVariable("id") Long id);
}

