package com.RaportService.RaportService.controllers;

import com.RaportService.RaportService.services.RaportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaportController {

    RaportService raportService;

    public RaportController(RaportService raportService) {
        this.raportService = raportService;
    }

    @GetMapping("/showraport")
    public String raport(){
        return raportService.xD();
    }
}
