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
        String[] jsons = jsonGeneratorService.generateJsonList(size);
        return ResponseEntity.ok(jsons);
    }



}

