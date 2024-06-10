package com.rekrutacja.CsvConverter.clients;

import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "json-service",url = "${application.config.json-url}")
public interface JsonGeneratorServiceClient {

    @GetMapping("/generate/json/{size}")
     String[] fetchJsonsFromFirstService(@PathVariable int size);


}
