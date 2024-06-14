package com.rekrutacja.CsvConverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class CsvConverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvConverterApplication.class, args);
	}

}
