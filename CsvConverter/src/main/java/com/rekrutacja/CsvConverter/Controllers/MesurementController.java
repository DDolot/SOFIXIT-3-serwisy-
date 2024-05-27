//package com.rekrutacja.CsvConverter.Controllers;
//
//import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
//import com.rekrutacja.CsvConverter.DTOs.PositionDTO;
//import com.rekrutacja.CsvConverter.Services.CsvConverterService;
//import com.rekrutacja.CsvConverter.Services.Measurement;
//import com.rekrutacja.CsvConverter.Services.MeasurementActuator;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//
//@RestController
//public class MesurementController {
//    private CsvConverterService csvConverterService;
//    private Measurement measurement;
//    private MeasurementActuator measurementActuator;
//
//    public static final Logger logger  = LoggerFactory.getLogger(MesurementController.class);
//
//
//    public MesurementController(CsvConverterService csvConverterService
//    ,Measurement measurement,MeasurementActuator measurementActuator) {
//        this.csvConverterService = csvConverterService;
//        this.measurement = measurement;
//        this.measurementActuator = measurementActuator;
//    }
//
//    @GetMapping("/test")
//    public ResponseEntity<Map<String, MeasurementDTO>> test(){
//        logger.info("z kontrolera ");
//        Map<String,MeasurementDTO> measures = new HashMap<>();
//        Instant start = Instant.now();
////        MeasurementDTO firstServicePerformance = csvConverterService.fetchPerformanceData();
//        Instant finish = Instant.now();
//        long timeElapsed = Duration.between(start, finish).toMillis();
////        firstServicePerformance.setTime(timeElapsed);
//
//
//
//
//
//        CompletableFuture<Void> futureConvert = CompletableFuture.runAsync(() -> {
//            String[] columns = {"_type","name","fullName"};
//            PositionDTO[] jsonData = csvConverterService.fetchData();
//            csvConverterService.convertToCSV(columns,jsonData);
//
//        });
//        CompletableFuture<Void> futureConvertActuator = CompletableFuture.runAsync(() -> {
//            String[] columns = {"_type","name","fullName"};
//            PositionDTO[] jsonData = csvConverterService.fetchData();
//            csvConverterService.convertToCSV(columns,jsonData);
//
//        });
//
////        MeasurementDTO measurementConvert = measurement.takeMeasurement(futureConvert);
//        MeasurementDTO measurementConvertActuator = measurementActuator.takeMeasurement(futureConvertActuator);
//
////        CompletableFuture<Void> futureCalculate = CompletableFuture.runAsync(() -> {
////            String[] columns = {"id+id^12", "sqrt(latitude)/sqrt(id)", "longitude-id"};
////            csvConverterService.calculate(columns,jsonData);
////
////        });
////
////        MeasurementDTO measurementCalculate = measurement.takeMeasurement(futureCalculate);
//
////        measures.put("convert",measurementConvert);
////        measures.put("calculate",measurementCalculate);
////        measures.put("firstServicePerformance",firstServicePerformance);
////        measures.put("my",measurementConvert);
//        measures.put("actuator",measurementConvertActuator);
//        return ResponseEntity.ok(measures);
//    }
//
//
//
////    @GetMapping("test")
////    public void test(){
////        CompletableFuture<PositionDTO[]> future = CompletableFuture.supplyAsync(
////                () -> csvConverterService.fetchData()
////        ).thenRunAsync()
//
//
//    }
//
//
//
