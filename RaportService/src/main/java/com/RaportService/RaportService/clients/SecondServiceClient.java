package com.RaportService.RaportService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "json-service",url = "${application.config.json-url}")
public interface SecondServiceClient {

    @GetMapping("/csv/calculate?params=id+id")
    ResponseEntity<double[]> fetchConstantData();


}

