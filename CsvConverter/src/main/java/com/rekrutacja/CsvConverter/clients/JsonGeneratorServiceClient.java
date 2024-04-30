package com.rekrutacja.CsvConverter.clients;

import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "json-service",url = "${application.config.json-url}")
public interface JsonGeneratorServiceClient {

    @GetMapping("/generate/json/100000")
    List<String> fetchJsonsFromFirstService();
    @GetMapping("/measurements/100000")
    MeasurementDTO fetchPerformanceData();


}
