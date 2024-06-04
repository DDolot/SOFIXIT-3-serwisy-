package com.rekrutacja.CsvConverter.Controllers;

import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.rekrutacja.CsvConverter.DTOs.PositionDTO;
import com.rekrutacja.CsvConverter.Services.CsvConverterService;
import com.rekrutacja.CsvConverter.Services.Measurement;
import com.rekrutacja.CsvConverter.Services.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CsvContoller {
    private CsvConverterService csvConverterService;
    private Observer observer;
    public CsvContoller(CsvConverterService csvConverterService, Measurement measurement, Observer observer) {

        this.csvConverterService = csvConverterService;

        this.observer = observer;


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
        HttpHeaders headers = new HttpHeaders();
        headers.add("JsonGeneratorServicePerformance",observer.getFetch().toString());
        headers.add("ConvertingToCsb",observer.getConvert().toString());
        return ResponseEntity.ok().headers(headers).body(csvData);

    }
    @GetMapping("/csv/calculate")
    public ResponseEntity<double[]> third(
            @RequestParam String[] params){
        PositionDTO[] jsonData = csvConverterService.fetchData();
        double[] response =  csvConverterService.calculate(params,jsonData);

        System.out.println(observer.getFetch());
        System.out.println(observer.getCalculate());


        HttpHeaders headers = new HttpHeaders();
        headers.add("JsonGeneratorServicePerformance",observer.getFetch().toString());
        headers.add("Calculating",observer.getCalculate().toString());
        return ResponseEntity.ok().headers(headers).body(response);



    }


}

