package com.bank.bank_api;


import com.bank.bank_api.infraestructure.dynamicfeign.EnableExtendedFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableExtendedFeignClients(basePackages = "com.bank.bank_api")
public class BankApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApiApplication.class, args);
	}

}
