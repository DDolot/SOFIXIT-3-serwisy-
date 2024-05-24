package com.rekrutacja.JsonGenerator.Controllers;
import com.rekrutacja.JsonGenerator.Services.JsonGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;

@RestController
public class JsonDataController {

    private JsonGeneratorService jsonGeneratorService;

    public JsonDataController(JsonGeneratorService jsonGeneratorService) {
        this.jsonGeneratorService = jsonGeneratorService;
    }
    @GetMapping(value = "/generate/json/{size}")
    public ResponseEntity<String[]> showJsonData(@PathVariable int size) {
        Instant start = Instant.now();

        String[] jsons = jsonGeneratorService.generateJsonList(size);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(timeElapsed);
        return ResponseEntity.ok(jsons);
    }



}

