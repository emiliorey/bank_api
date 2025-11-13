package com.bank.bank_api.infraestructure.dynamicfeign;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/external")
@RequiredArgsConstructor
@Scope("prototype")
public class ExternalApiController {

    private final ExternalApiClientService externalApiClientService;

    @GetMapping("/post/{id}")
    public ResponseEntity<Map<String, String>> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(externalApiClientService.getPost(id));
    }
}