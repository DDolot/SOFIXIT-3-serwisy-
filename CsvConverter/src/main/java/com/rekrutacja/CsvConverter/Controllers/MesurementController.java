package com.rekrutacja.CsvConverter.Controllers;

import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.rekrutacja.CsvConverter.DTOs.PositionDTO;
import com.rekrutacja.CsvConverter.Services.CsvConverterService;
import com.rekrutacja.CsvConverter.Services.Measurement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class MesurementController {
    private CsvConverterService csvConverterService;
    private Measurement measurement;

    public MesurementController(CsvConverterService csvConverterService
    ,Measurement measurement) {
        this.csvConverterService = csvConverterService;
        this.measurement = measurement;
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, MeasurementDTO>> test(){
        Map<String,MeasurementDTO> measures = new HashMap<>();
        Instant start = Instant.now();
        MeasurementDTO firstServicePerformance = csvConverterService.fetchPerformanceData();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        firstServicePerformance.setTime(timeElapsed);


        List<PositionDTO> jsonData = csvConverterService.fetchData();

        CompletableFuture<Void> futureConvert = CompletableFuture.runAsync(() -> {
            String[] columns = {"_type"};

            csvConverterService.convertToCSV(columns,jsonData);

        });
        MeasurementDTO measurementConvert = measurement.takeMeasurement(futureConvert);

        CompletableFuture<Void> futureCalculate = CompletableFuture.runAsync(() -> {
            String[] columns = {"id+id^12", "sqrt(latitude)/sqrt(id)", "longitude-id"};
            csvConverterService.calculate(columns,jsonData);

        });

        MeasurementDTO measurementCalculate = measurement.takeMeasurement(futureCalculate);

        measures.put("convert",measurementConvert);
        measures.put("calculate",measurementCalculate);
        measures.put("firstServicePerformance",firstServicePerformance);
        return ResponseEntity.ok(measures);
    }

//    @GetMapping("test")
//    public ResponseEntity<MeasurementDTO> test(){
//        MeasurementDTO stringMeasurementDTOMap = csvConverterService.fetchPerformanceData();
//        return ResponseEntity.ok(stringMeasurementDTOMap);
//    }


}
