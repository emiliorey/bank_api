package com.bank.bank_api.infraestructure.dynamicfeign;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientConfigController {

    private final DynamicClientService dynamicClientService;

    @PostMapping("/change-url")
    public String changeUrl(@RequestParam String newUrl) {
        dynamicClientService.changeBaseUrl(newUrl);
        return "URL actualizada a: " + newUrl;
    }

    @GetMapping("/dynamic-beans/default-configuration")
    public List<DynamicFeignInfo> getBeansDynamicFeignClientDefaultConfiguration() {
        return dynamicClientService.getBeansDynamicFeignClientDefaultConfiguration();
    }

    @GetMapping("/dynamic-beans/current-configuration")
    public List<DynamicFeignInfo> getBeansDynamicFeignClientCurrentConfiguration() {
        return dynamicClientService.getBeansDynamicFeignClientCurrentConfiguration();
    }
}
