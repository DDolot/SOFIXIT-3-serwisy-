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

        Instant start = Instant.now();
        ResponseEntity<String> convert = client.convert(size * 1000, csvColumns);
        ResponseEntity<double[]> calculate = client.calculate(size * 1000, mathOperations);
        Instant finish = Instant.now();
        long duration = Duration.between(start, finish).toMillis() / 2;

        addModelAttributes(model, convert, calculate, duration);
        return "raportView";
    }

    private static void addModelAttributes(Model model, ResponseEntity<String> convert, ResponseEntity<double[]> calculate, long duration) {
        model.addAttribute("MeasurementForFirstService", convert.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondService", convert.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("MeasurementForFirstServiceCalculate", calculate.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondServiceCalculate", calculate.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("time2to3", duration);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(FeignException e) {
        return ResponseEntity.badRequest().body("Request problem, please check if column names are valid");
    }
}
