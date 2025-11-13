package com.bank.bank_api.infraestructure.dynamicfeign;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Scope("prototype")
@RequiredArgsConstructor
public class ExternalApiClientService {

    private final ExternalApiClient externalApiClient;

    public Map<String, String> getPost(Long id) {
        return externalApiClient.getPost(id);
    }

}
