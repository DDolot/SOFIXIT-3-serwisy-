package com.RaportService.RaportService.controllers;

import com.RaportService.RaportService.clients.SecondServiceClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@Slf4j
public class RaportController {

    private SecondServiceClient client;
    @Autowired
    private RestTemplate restTemplate;


    public RaportController(SecondServiceClient client) {
        this.client = client;

    }

    @GetMapping("/showraport")
    public String raport() {
        String url = "http://localhost:8081" + "/csv/calculate?params=id+2";
        ResponseEntity<double[]> response = restTemplate.getForEntity(url, double[].class);

        System.out.println(response.getHeaders());
        return response.getHeaders().getFirst("JsonGeneratorServicePerformance");
    }


    }

