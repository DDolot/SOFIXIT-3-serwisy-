package com.rekrutacja.SecondService.Controllers;

import com.rekrutacja.SecondService.Services.Measurement;
import com.rekrutacja.SecondService.dtos.MeasurementDTO;
import com.rekrutacja.SecondService.dtos.PositionDTO;
import com.rekrutacja.SecondService.Services.SecondService;
import com.rekrutacja.SecondService.Services.SecondServiceDifferent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@RestController
public class SecondController {
    private SecondServiceDifferent secondServiceDifferent;
    private Measurement measurement;
    private final String path = "static/file.csv";
    public SecondController(Measurement measurement, SecondServiceDifferent secondServiceDifferent) {
        this.measurement = measurement;
        this.secondServiceDifferent = secondServiceDifferent;
    }

//    @GetMapping("/csv")
//    public String ConstantCsvFormat(){
//        JsonModelDTO dto = new JsonModelDTO(JsonModel);
//        secondServiceDifferent.fetchData()
//        return secondServiceDifferent.firstEP();
//
//    }

    @GetMapping("/csv/constant")
    public ResponseEntity<String> first(){
        List<PositionDTO> jsonData = secondServiceDifferent.fetchData();
        String[] columns = {"_type","id","name","type","latitude","longitude"};
        String csvData = secondServiceDifferent.convertToCSV(columns,jsonData);
        return ResponseEntity.ok(csvData);
    }
    @GetMapping("/csv/structure")
    public ResponseEntity<String> second(
            @RequestParam String[] params){
        List<PositionDTO> jsonData = secondServiceDifferent.fetchData();

        String csvData = secondServiceDifferent.convertToCSV(params,jsonData);
        return ResponseEntity.ok(csvData);
    }




    @GetMapping("/csv/calculate")
    public ResponseEntity<List<Double>> third(
            @RequestParam List<String> params){
        List<PositionDTO> jsonData = secondServiceDifferent.fetchData();
        List<String> modifiedParams = params.stream()
                .map(s -> s.replace(" ", "+"))
                .toList();


            return ResponseEntity.ok(secondServiceDifferent.calculate(modifiedParams,jsonData));


    }

    @GetMapping("/test")
    public ResponseEntity<Map<String,MeasurementDTO>> test(){
        Map<String,MeasurementDTO> mesures = new HashMap<>();
        MeasurementDTO firstServicePerformance = secondServiceDifferent.fetchPerformanceData();
        CompletableFuture<Void> futureConvert = CompletableFuture.runAsync(() -> {
            List<PositionDTO> jsonData = secondServiceDifferent.fetchData();
            String[] columns = {"_type","id","name","type","latitude","longitude"};
            secondServiceDifferent.convertToCSV(columns,jsonData);

        });
        MeasurementDTO measurementConvert = measurement.takeMeasurement(futureConvert);

        CompletableFuture<Void> futureCalculate = CompletableFuture.runAsync(() -> {
            List<PositionDTO> jsonData = secondServiceDifferent.fetchData();
            List<String> columns = List.of("id","latitude","longitude");
            secondServiceDifferent.calculate(columns,jsonData);

        });

        MeasurementDTO measurementCalculate = measurement.takeMeasurement(futureCalculate);

        mesures.put("convert",measurementConvert);
        mesures.put("calculate",measurementCalculate);
        mesures.put("firstServicePerformance",firstServicePerformance);
        return ResponseEntity.ok(mesures);
    }

}

