package com.rekrutacja.CsvConverter.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import com.rekrutacja.CsvConverter.DTOs.PositionDTO;
import com.rekrutacja.CsvConverter.clients.*;
import lombok.SneakyThrows;
import net.objecthunter.exp4j.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import net.objecthunter.exp4j.*;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
public class CsvConverterService {
    private JsonGeneratorServiceClient client;
    private final ObjectMapper OM = new ObjectMapper();
    public CsvConverterService(JsonGeneratorServiceClient client) {
        this.client = client;
    }

    @SneakyThrows(RuntimeException.class)
    public PositionDTO[] fetchData() {
        String[] jsons = client.fetchJsonsFromFirstService();
        return Arrays.stream(jsons)
                .map(json -> {
                    try {
                        return OM.readValue(json, PositionDTO.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toArray(PositionDTO[]::new);

    }

    public String convertToCSV(String[] columns,PositionDTO[] jsonData) {
        StringWriter csvData = new StringWriter();
        for (PositionDTO json : jsonData) {
            List<String> rowData = new ArrayList<>();
            for (String column : columns) {
                try {
                    Object columnValue = getColumnValue(json, column);
                    rowData.add(String.valueOf(columnValue));
                } catch (Exception e) {
                    e.printStackTrace();
                    rowData.add("N/A");
                }
            }
            String row = String.join(",", rowData);
            csvData.append(row).append("\n");
        }
        return csvData.toString();
    }

    private Object getColumnValue(PositionDTO dto, String column) {

        Map<String, Object> map = OM.convertValue(dto, new TypeReference<Map<String, Object>>() {
        });
        return map.get(column);
    }

    public double[] calculate(String[] columns,PositionDTO[] jsonData) {
        int resultSize = jsonData.length;
        int expressionsSize = columns.length;
        double[] results = new double[resultSize * expressionsSize];
        int i = 0;
        String id = "id";
        String latitude = "latitude";
        String longitude = "longitude";
        String location = "location_id";

        for (PositionDTO json : jsonData) {
            for (String expression : columns) {
                Expression exp = new ExpressionBuilder(expression)
                        .variables(id, latitude, longitude, location)
                        .build()
                        .setVariable(id, json.getId())
                        .setVariable(latitude, json.getLatitude())
                        .setVariable(longitude, json.getLongitude())
                        .setVariable(location, json.getLocationId());

                double evaluate = exp.evaluate();
                results[i++] = evaluate;
            }
        }

        return results;
    }


}