package com.RaportService.RaportService.controllers;

import com.RaportService.RaportService.clients.SecondServiceClient;
import com.RaportService.RaportService.services.RaportService;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;

@Controller
@RequestMapping("/raport")

public class RaportController {

    private final RaportService raportService;
    private final SecondServiceClient client;

    public RaportController(RaportService raportService, SecondServiceClient client) {
        this.raportService = raportService;
        this.client = client;
    }

    @GetMapping("/{size}K")
    public String generateRaport(
            @PathVariable int size,
            @RequestParam String[] csvColumns,
            @RequestParam String[] mathOperations,
            Model model) {

        Instant startConvertRequest = Instant.now();
        ResponseEntity<String> convert = client.convert(size * 1000, csvColumns);
        Instant stopConvertRequest = Instant.now();
        long durationConvertRequest = Duration.between(startConvertRequest, stopConvertRequest).toMillis();

        Instant startCalculateRequest = Instant.now();
        ResponseEntity<double[]> calculate = client.calculate(size * 1000, mathOperations);
        Instant stopCalculateRequest = Instant.now();
        long durationCalculateRequest = Duration.between(startCalculateRequest, stopCalculateRequest).toMillis();

        addModelAttributes(model, convert, calculate, durationConvertRequest,durationCalculateRequest);
        return "raportView";
    }

    private static void addModelAttributes(Model model, ResponseEntity<String> convert, ResponseEntity<double[]> calculate, long durationConvertRequest,long durationCalculateRequest) {
        model.addAttribute("MeasurementForFirstService", convert.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondService", convert.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("MeasurementForFirstServiceCalculate", calculate.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondServiceCalculate", calculate.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("time2to3Convert", durationConvertRequest);
        model.addAttribute("time2to3Calculate", durationCalculateRequest);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(FeignException e) {
        return ResponseEntity.badRequest().body("Request problem, please check if column names are valid");
    }
}
