package com.RaportService.RaportService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RaportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaportServiceApplication.class, args);
	}

}
