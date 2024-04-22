package com.rekrutacja.CsvConverter.Controllers;

import com.rekrutacja.CsvConverter.Services.Measurement;
import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.rekrutacja.CsvConverter.DTOs.PositionDTO;
import com.rekrutacja.CsvConverter.Services.CsvConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class CsvContoller {
    private CsvConverterService csvConverterService;
    private Measurement measurement;
    public CsvContoller(Measurement measurement, CsvConverterService csvConverterService) {
        this.measurement = measurement;
        this.csvConverterService = csvConverterService;
    }

    @GetMapping("/csv/constant")
    public ResponseEntity<String> first(){
        List<PositionDTO> jsonData = csvConverterService.fetchData();
        String[] columns = {"_type","id","name","type","latitude","longitude"};
        String csvData = csvConverterService.convertToCSV(columns,jsonData);
        return ResponseEntity.ok(csvData);
    }
    @GetMapping("/csv/structure")
    public ResponseEntity<String> second(
            @RequestParam String[] params){
        List<PositionDTO> jsonData = csvConverterService.fetchData();

        String csvData = csvConverterService.convertToCSV(params,jsonData);
        return ResponseEntity.ok(csvData);
    }




    @GetMapping("/csv/calculate")
    public ResponseEntity<List<Double>> third(
            @RequestParam List<String> params){
        List<PositionDTO> jsonData = csvConverterService.fetchData();
        List<String> modifiedParams = params.stream()
                .map(s -> s.replace(" ", "+"))
                .toList();
        return ResponseEntity.ok(csvConverterService.calculate(modifiedParams,jsonData));
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String,MeasurementDTO>> test(){
        Map<String,MeasurementDTO> measures = new HashMap<>();

        MeasurementDTO firstServicePerformance = csvConverterService.fetchPerformanceData();

        long OnetoTwoTimeStart = System.currentTimeMillis();
        List<PositionDTO> jsonData = csvConverterService.fetchData();
        long sOnetoTwoTimeStop = System.currentTimeMillis();

        CompletableFuture<Void> futureConvert = CompletableFuture.runAsync(() -> {
            String[] columns = {"_type","id","name","type","latitude","longitude"};
            csvConverterService.convertToCSV(columns,jsonData);

        });
        MeasurementDTO measurementConvert = measurement.takeMeasurement(futureConvert);

        CompletableFuture<Void> futureCalculate = CompletableFuture.runAsync(() -> {
            List<String> columns = List.of("id","latitude","longitude");
            csvConverterService.calculate(columns,jsonData);

        });

        MeasurementDTO measurementCalculate = measurement.takeMeasurement(futureCalculate);

        measures.put("convert",measurementConvert);
        measures.put("calculate",measurementCalculate);
        measures.put("firstServicePerformance",firstServicePerformance);
        return ResponseEntity.ok(measures);
    }

}

