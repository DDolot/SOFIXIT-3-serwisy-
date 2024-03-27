package com.rekrutacja.FirstService.Controllers;
import com.rekrutacja.FirstService.Services.FirstService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.util.List;


@RestController
public class FirstController {

    private FirstService firstService;

    public FirstController(FirstService firstService) {
        this.firstService = firstService;
    }


    @GetMapping(value = "/generate/json/{size}")
    public ResponseEntity<List<String>> generateJsonFromModel(@PathVariable int size) {

        return ResponseEntity.ok(firstService.generateJsonList(size));
    }



}

