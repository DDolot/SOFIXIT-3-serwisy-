package com.rekrutacja.SecondService.clients;

import com.rekrutacja.SecondService.Services.Measurement;
import com.rekrutacja.SecondService.dtos.MeasurementDTO;
import com.rekrutacja.SecondService.dtos.PositionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "json-service",url = "${application.config.json-url}")
public interface firstServiceClient {

    @GetMapping("/generate/json/10000")
    List<String> fetchJsonsFromFirstService();
    @GetMapping("/measurements/10000")
    MeasurementDTO fetchPerformanceData();


}
