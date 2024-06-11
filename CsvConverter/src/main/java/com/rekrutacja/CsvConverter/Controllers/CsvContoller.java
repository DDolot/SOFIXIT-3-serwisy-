package com.rekrutacja.CsvConverter.Controllers;

import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.rekrutacja.CsvConverter.DTOs.PositionDTO;
import com.rekrutacja.CsvConverter.Services.CsvConverterService;

import com.rekrutacja.CsvConverter.Observables.Observer;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CsvContoller {
    private CsvConverterService csvConverterService;
    private Observer observer;

    public CsvContoller(CsvConverterService csvConverterService, Observer observer) {

        this.csvConverterService = csvConverterService;

        this.observer = observer;


    }

    @GetMapping(value = "/csv/constant/{size}")
    public ResponseEntity<String> StructuredCsv(
            @PathVariable int size
    ){
        String[] columns = {"_type","id","name","type","latitude","longitude"};
        PositionDTO[] jsonData = csvConverterService.fetchData(size);
        String csvData = csvConverterService.convertToCSV(columns,jsonData);

        MeasurementDTO fetch = observer.getFetch();
        MeasurementDTO convert = observer.getConvert();

        return ResponseEntity.ok().headers(getHttpHeaders(fetch, convert)).body(csvData);


    }
    @GetMapping("/csv/structure/{size}")
    public ResponseEntity<String> CustomCsv(
            @PathVariable int size,
            @RequestParam String[] params){
        PositionDTO[] jsonData = csvConverterService.fetchData(size);
        String csvData = csvConverterService.convertToCSV(params,jsonData);

        MeasurementDTO fetch = observer.getFetch();
        MeasurementDTO convert = observer.getConvert();

        return ResponseEntity.ok().headers(getHttpHeaders(fetch, convert)).body(csvData);

    }
    @GetMapping("/csv/calculate/{size}")
    public ResponseEntity<double[]> calculate(
            @PathVariable int size,
            @RequestParam String[] params){
        PositionDTO[] jsonData = csvConverterService.fetchData(size);
        double[] response =  csvConverterService.calculate(params,jsonData);

        MeasurementDTO fetch = observer.getFetch();
        MeasurementDTO calculate = observer.getCalculate();

        HttpHeaders headers = getHttpHeaders(fetch, calculate);

        return ResponseEntity.ok().headers(headers).body(response);



    }

    private HttpHeaders getHttpHeaders(MeasurementDTO jsonService, MeasurementDTO csvService) {
        HttpHeaders headers = new HttpHeaders();

        headers.put("MeasurementForFirstService", List.of(jsonService.getProcessCpuLoad().toString(),
                jsonService.getUsedMemorySize().toString(),String.valueOf(jsonService.getTime())));

        headers.put("MeasurementForSecondService", List.of(csvService.getProcessCpuLoad().toString(),
                csvService.getUsedMemorySize().toString(),String.valueOf(csvService.getTime())));

        return headers;
    }

    @ExceptionHandler
    private ResponseEntity<?> mathOperationExeptionHandler(UnknownFunctionOrVariableException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler
    private ResponseEntity<?> convertingToCsvExeptionHandler(RuntimeException e){
        return ResponseEntity.badRequest().body("No such column");
    }

}

