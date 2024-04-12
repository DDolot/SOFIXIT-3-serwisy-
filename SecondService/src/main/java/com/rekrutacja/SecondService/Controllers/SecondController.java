package com.rekrutacja.SecondService.Controllers;

import com.opencsv.CSVWriter;
import com.rekrutacja.SecondService.Models.JsonModel;
import com.rekrutacja.SecondService.Services.SecondService;
import com.rekrutacja.SecondService.Services.SecondServiceDifferent;
import com.rekrutacja.SecondService.dtos.JsonModelDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SecondController {

    private SecondService secondService;
    private SecondServiceDifferent secondServiceDifferent;
    private final String path = "static/file.csv";
    public SecondController(SecondService secondService,SecondServiceDifferent secondServiceDifferent) {
        this.secondService = secondService;
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
        List<JsonModel> jsonData = secondServiceDifferent.fetchData();
        String[] columns = {"_type","id","name","type","latitude","longitude"};
        String csvData = secondServiceDifferent.convertToCSV(columns,jsonData);
        return ResponseEntity.ok(csvData);
    }
    @GetMapping("/csv/structure")
    public ResponseEntity<String> second(
            @RequestParam String[] params){
        List<JsonModel> jsonData = secondServiceDifferent.fetchData();

        String csvData = secondServiceDifferent.convertToCSV(params,jsonData);
        return ResponseEntity.ok(csvData);
    }


    @GetMapping("/csv/calculate")
    public ResponseEntity<List<Double>> third(
            @RequestParam List<String> params){
        List<JsonModel> jsonData = secondServiceDifferent.fetchData();
        List<String> modifiedParams = params.stream()
                .map(s -> s.replace(" ", "+"))
                .toList();


            return ResponseEntity.ok(secondServiceDifferent.calculate(modifiedParams,jsonData));


    }

}

