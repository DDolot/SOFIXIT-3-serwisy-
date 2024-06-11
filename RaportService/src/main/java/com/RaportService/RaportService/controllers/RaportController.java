package com.RaportService.RaportService.controllers;

import com.RaportService.RaportService.clients.SecondServiceClient;

import com.RaportService.RaportService.services.RaportService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Repeatable;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
public class RaportController {

    private RaportService raportService;

    private SecondServiceClient client;

    public RaportController(RaportService raportService, SecondServiceClient client) {
        this.raportService = raportService;
        this.client = client;
    }

    @GetMapping("/1Kraport")

    public String raport1K(
            @RequestParam String[] csvColumns,
            @RequestParam String[] mathOperations
            ,Model model) {

        Instant start = Instant.now();
        ResponseEntity<String> convert = client.convert(1000, csvColumns);
        ResponseEntity<double[]> calculate = client.calculate(1000,mathOperations);
        Instant finish = Instant.now();
        long duration = Duration.between(start, finish).toMillis() / 2;

        addModelAtributes(model, convert, calculate, duration);


        return "raportView";
    }  @GetMapping("/10Kraport")

    public String raport10K(
            @RequestParam String[] csvColumns,
            @RequestParam String[] mathOperations
            ,Model model) {

        Instant start = Instant.now();
        ResponseEntity<String> convert = client.convert(10000, csvColumns);
        ResponseEntity<double[]> calculate = client.calculate(10000,mathOperations);
        Instant finish = Instant.now();
        long duration = Duration.between(start, finish).toMillis() / 2;

        addModelAtributes(model, convert, calculate, duration);


        return "raportView";
    }

    @GetMapping("/100Kraport")
    public String raport100K(
            @RequestParam String[] csvColumns,
            @RequestParam String[] mathOperations
            ,Model model) {

        Instant start = Instant.now();
        ResponseEntity<String> convert = client.convert(100000, csvColumns);
        ResponseEntity<double[]> calculate = client.calculate(100000,mathOperations);
        Instant finish = Instant.now();
        long duration = Duration.between(start, finish).toMillis() / 2;

        addModelAtributes(model, convert, calculate, duration);


        return "raportView";
    }


    private static void addModelAtributes(Model model, ResponseEntity<String> convert, ResponseEntity<double[]> calculate, long duration) {
        model.addAttribute("MeasurementForFirstService", convert.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondService", convert.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("MeasurementForFirstServiceCalculate", calculate.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondServiceCalculate", calculate.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("time2to3", duration);
    }
    @ExceptionHandler
    private ResponseEntity<?> reportExeptionHandler(FeignException e){
        return ResponseEntity.badRequest().body("Request problem,please check if column names are valid");
    }

}

