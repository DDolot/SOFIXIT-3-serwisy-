package com.RaportService.RaportService.controllers;

import com.RaportService.RaportService.clients.SecondServiceClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;





@RestController
@Slf4j
public class RaportController {

    private SecondServiceClient client;

    public RaportController(SecondServiceClient client) {
        this.client = client;
    }




        @GetMapping("/showraport")
    public String  raport(){
        return client.fetchConstantData();
    }
//    @GetMapping("/s")
//
//    public RecordingStream raport() throws IOException,ParseException {
//        Configuration c = Configuration.getConfiguration("default");
//        try (var rs = new RecordingStream(c)) {
//            // Konfiguracja profiler'a - wybierz zdarzenia do śledzenia
//            rs.enable("jdk.CPULoad").withPeriod(Duration.ofMillis(1));
//            rs.enable("jdk.JVMInformation");
//
//            // Rozpoczęcie profilowania
//            rs.startAsync();
//
//            // Wywołanie metody do profilowania
//            client.fetchConstantData();
//            return rs;
//
//        }

    }

