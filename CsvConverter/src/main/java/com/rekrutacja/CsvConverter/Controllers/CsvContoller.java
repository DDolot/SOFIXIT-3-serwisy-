package com.rekrutacja.CsvConverter.Controllers;

import com.rekrutacja.CsvConverter.Services.Measurement;
import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.rekrutacja.CsvConverter.DTOs.PositionDTO;
import com.rekrutacja.CsvConverter.Services.CsvConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class CsvContoller {
    private CsvConverterService csvConverterService;

    public CsvContoller(CsvConverterService csvConverterService) {
        this.csvConverterService = csvConverterService;
    }

//    @GetMapping("/csv/constant")
//    public ResponseEntity<String> first(){
//        List<PositionDTO> jsonData = csvConverterService.fetchData();
//        String[] columns = {"_type","id","name","type","latitude","longitude"};
//        String csvData = csvConverterService.convertToCSV(columns,jsonData);
//        return ResponseEntity.ok(csvData);
//    }
//    @GetMapping("/csv/structure")
//    public ResponseEntity<String> second(
//            @RequestParam String[] params){
//        List<PositionDTO> jsonData = csvConverterService.fetchData();
//        String csvData = csvConverterService.convertToCSV(params,jsonData);
//        return ResponseEntity.ok(csvData);
//    }
//
//
//
//
//    @GetMapping("/csv/calculate")
//    public ResponseEntity<double[]> third(
//            @RequestParam String[] params){
//        List<PositionDTO> jsonData = csvConverterService.fetchData();
//        ResponseEntity<double[]> response
//                = ResponseEntity.ok(csvConverterService.calculate(params,jsonData));
//
//        return response;
//    }



}

