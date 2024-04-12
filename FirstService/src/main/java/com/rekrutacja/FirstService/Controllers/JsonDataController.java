package com.rekrutacja.FirstService.Controllers;
import com.rekrutacja.FirstService.Services.FirstService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
public class JsonDataController {

    private FirstService firstService;

    public JsonDataController(FirstService firstService) {
        this.firstService = firstService;
    }

    @GetMapping(value = "/generate/json/{size}")
    public ResponseEntity<String[]> showJsonData(@PathVariable int size) {
        String[] jsons = firstService.generateJsonList(size);


        return ResponseEntity.ok(jsons);
    }



}

