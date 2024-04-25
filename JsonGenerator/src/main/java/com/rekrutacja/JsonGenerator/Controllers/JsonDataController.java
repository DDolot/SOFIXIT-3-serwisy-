package com.rekrutacja.JsonGenerator.Controllers;
import com.rekrutacja.JsonGenerator.Services.JsonGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class JsonDataController {

    private JsonGeneratorService jsonGeneratorService;

    public JsonDataController(JsonGeneratorService jsonGeneratorService) {
        this.jsonGeneratorService = jsonGeneratorService;
    }
    @GetMapping(value = "/generate/json/{size}")
    public ResponseEntity<String[]> showJsonData(@PathVariable int size) {
        long startTime = System.currentTimeMillis();
        String[] jsons = jsonGeneratorService.generateJsonList(size);
        long stopTime = System.currentTimeMillis();
        System.out.println(jsons[0]);
        System.out.println(stopTime-startTime);
        return ResponseEntity.ok(jsons);
    }



}

