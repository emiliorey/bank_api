package com.bank.bank_api.infraestructure.repositories.externalservices;

import com.bank.bank_api.infraestructure.dtos.response.BankResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RefreshScope
@FeignClient(name = "relationFeignClient", url = "${ms.bank.url}")
public interface BankFeignClient {

    @GetMapping("/bank/{uid}")
    BankResponseDTO getByUid(@NotNull @PathVariable String uid);
}
