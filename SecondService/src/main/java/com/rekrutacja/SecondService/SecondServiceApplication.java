package com.rekrutacja.SecondService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@EnableFeignClients
@SpringBootApplication
public class SecondServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondServiceApplication.class, args);
	}
	@Configuration
	public class RestTemplateConfig {

		@Bean
		public RestTemplate restTemplate() {
			return new RestTemplate();
		}
	}


}
