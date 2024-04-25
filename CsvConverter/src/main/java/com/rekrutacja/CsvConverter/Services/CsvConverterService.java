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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CsvConverterService {
    @Autowired
    private JsonGeneratorServiceClient client;


//    public CsvConverterService(JsonGeneratorServiceClient client) {
//        this.client = client;
//    }
    @SneakyThrows
    public List<PositionDTO> fetchData() {
        ObjectMapper om = new ObjectMapper();
        List<String> jsons = client.fetchJsonsFromFirstService();

        return jsons.stream()
                .map(json -> {
                    try {
                        return om.readValue(json, PositionDTO.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

    }

    public String convertToCSV(String[] columns, List<PositionDTO> jsonData) {

        StringWriter csvData = new StringWriter();

        for (PositionDTO json : jsonData) {
            List<String> rowData = new ArrayList<>();
            for (String column : columns) {
                try {

                    Object columnValue = getColumnValue2(json, column);
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

    private Object getColumnValue2(PositionDTO dto, String column){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(dto, new TypeReference<Map<String, Object>>() {});
        return map.get(column);
    }


    private Object getColumnValue(PositionDTO json, String column) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String methodName = "get" + column.substring(0, 1).toUpperCase() + column.substring(1);

        Method method = json.getClass().getMethod(methodName);

        return method.invoke(json);
    }

    public double[] calculate(String[] columns, List<PositionDTO> jsonData) {
        double[] results = new double[jsonData.size()];
        double evaluate = 0;
        String id = "id";
        String latitdue = "latitude";
        String longitude = "longitude";
        String location = "location_id";

        for (int j =0;j<jsonData.size();j++) {
            PositionDTO json = jsonData.get(j);
            for (int i = 0;i<columns.length;i++) {
                String expression = columns[i];
                Expression exp = new ExpressionBuilder(expression)
                        .variables(id,latitdue,longitude,location)
                        .build()
                        .setVariable(id, json.getId())
                        .setVariable(latitdue, json.getLatitude())
                        .setVariable(longitude, json.getLongitude())
                        .setVariable(location, json.getLocationId());

                evaluate = exp.evaluate();

            }
            results[j] = evaluate;
        }
        return results;
    }

    public ResponseEntity<MeasurementDTO> fetchPerformanceData() {
        return client.fetchPerformanceData();
    }
}
