package com.rekrutacja.CsvConverter.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "json-service",url = "http://${application.config.hostname}:8080")
public interface JsonGeneratorServiceClient {

    @GetMapping("/generate/json/{size}")
     String[] fetchJsonsFromFirstService(@PathVariable int size);


}
