package com.RaportService.RaportService.controllers;

import com.RaportService.RaportService.clients.SecondServiceClient;

import com.RaportService.RaportService.services.RaportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

        model.addAttribute("MeasurementForFirstService",convert.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondService",convert.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("MeasurementForFirstServiceCalculate",calculate.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondServiceCalculate",calculate.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("time2to3",duration);



        return "hello";
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

        model.addAttribute("MeasurementForFirstService",convert.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondService",convert.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("MeasurementForFirstServiceCalculate",calculate.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondServiceCalculate",calculate.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("time2to3",duration);



        return "hello";
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

        model.addAttribute("MeasurementForFirstService",convert.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondService",convert.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("MeasurementForFirstServiceCalculate",calculate.getHeaders().get("MeasurementForFirstService"));
        model.addAttribute("MeasurementForSecondServiceCalculate",calculate.getHeaders().get("MeasurementForSecondService"));

        model.addAttribute("time2to3",duration);



        return "hello";
    }



    }

