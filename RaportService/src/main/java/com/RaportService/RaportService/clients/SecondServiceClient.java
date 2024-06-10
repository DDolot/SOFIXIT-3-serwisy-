package com.RaportService.RaportService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "json-service",url = "${application.config.json-url}")
public interface SecondServiceClient {

    @GetMapping("/csv/structure/{size}")
    ResponseEntity<String> convert(@PathVariable int size, @RequestParam String[] params);
    @GetMapping("/csv/calculate/{size}")

    ResponseEntity<double[]> calculate(@PathVariable int size, @RequestParam String[] params);
    @GetMapping("/csv/constant/{size}")

    ResponseEntity<String> constant(@PathVariable int size, @RequestParam String[] params);



}

