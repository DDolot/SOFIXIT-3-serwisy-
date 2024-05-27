package com.rekrutacja.CsvConverter.Controllers;

import com.rekrutacja.CsvConverter.DTOs.PositionDTO;
import com.rekrutacja.CsvConverter.Services.Aspect;
import com.rekrutacja.CsvConverter.Services.CsvConverterService;
import com.rekrutacja.CsvConverter.Services.Measurement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CsvContoller {
    private CsvConverterService csvConverterService;
    private Measurement measurement;


    public CsvContoller(CsvConverterService csvConverterService,Measurement measurement) {

        this.csvConverterService = csvConverterService;
        this.measurement = measurement;

    }



    @GetMapping(value = "/csv/constant")
    public ResponseEntity<String> first(){

        PositionDTO[] jsonData = csvConverterService.fetchData();
        String[] columns = {"_type","id","name","type","latitude","longitude"};


        String csvData = csvConverterService.convertToCSV(columns,jsonData);
        return ResponseEntity.ok(csvData);

    }
    @GetMapping("/csv/structure")
    public ResponseEntity<String> second(
            @RequestParam String[] params){
        PositionDTO[] jsonData = csvConverterService.fetchData();
        String csvData = csvConverterService.convertToCSV(params,jsonData);

        return ResponseEntity.ok(csvData);

    }
    @GetMapping("/csv/calculate")
    public ResponseEntity<double[]> third(
            @RequestParam String[] params){
        PositionDTO[] jsonData = csvConverterService.fetchData();
        ResponseEntity<double[]> response
                = ResponseEntity.ok(csvConverterService.calculate(params,jsonData));



        return response;
    }

}

