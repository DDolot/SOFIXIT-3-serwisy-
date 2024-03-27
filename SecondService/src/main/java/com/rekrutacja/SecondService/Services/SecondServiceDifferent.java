package com.rekrutacja.SecondService.Services;

import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rekrutacja.SecondService.Models.JsonModel;
import com.rekrutacja.SecondService.clients.*;
import net.objecthunter.exp4j.Expression;
import org.springframework.stereotype.Service;
import net.objecthunter.exp4j.*;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.actuate.metrics.*;


@Service
public class SecondServiceDifferent {

    private firstServiceClient client;


    public SecondServiceDifferent(firstServiceClient client) {
        this.client = client;
    }

    public List<JsonModel> fetchData(){
        ObjectMapper om = new ObjectMapper();
        List<String> jsons = client.fetchJsonsFromFirstService();

        return jsons.stream()
                .map(json -> {
                    try {
                        return om.readValue(json, JsonModel.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

    }

//    public <T> List<T> fetchData(Class<T> jsonModel){
//        ObjectMapper om = new ObjectMapper();
//        List<String> jsons = client.fetchJsonsFromFirstService();
//
//        return jsons.stream()
//                .map(json -> {
//                    try {
//                        return om.readValue(json, jsonModel);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .collect(Collectors.toList());
//
//    }

    public String firstEP(){
        StringWriter csvData = new StringWriter();



        for(JsonModel json: fetchData()){
            String row = String.join(",",
                    json.get_type(),
                    json.getId().toString(),
                    json.getName(),
                    json.getType(),
                    json.getLatitude().toString(),
                    json.getLongitude().toString()



            );


            csvData.append(row).append("\n");
        }
        return csvData.toString();
    }
    public String convertToCSV(String[] columns,List<JsonModel> jsonData) {

        StringWriter csvData = new StringWriter();

        for (JsonModel json : jsonData) {
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

//    public void calculate(List<String> columns,List<JsonModel> jsonData) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
//        for(JsonModel json : jsonData){
//            // expression = latitude*longitude
//            for(String expresion:columns) {
//                // CTC = [longitude,latitude]
//                String[] columnToCalculate = expresion.split("[-+*/]");
//                // operators = [*]
//                char[] operators = expresion.replaceAll("[a-zA-Z0-9]", "").toCharArray();
////
//                for(String s: columnToCalculate){
//                    Object columnValue = getColumnValue(json, s);
//
//                }
//
//        }
//
//
//        }
//
//    }

    private Object getColumnValue(JsonModel json, String column) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String methodName = "get" + column.substring(0, 1).toUpperCase() + column.substring(1);

        Method method = json.getClass().getMethod(methodName);

        return method.invoke(json);
    }

    public List<Double> calculate(List<String> columns,List<JsonModel> jsonData){

        List<Double> results = new ArrayList<>();
            for(JsonModel json:jsonData){
            for(String expression:columns){

                Expression exp = new ExpressionBuilder(expression)
                        .variables("id","latitude","longitude","location_id")
                        .build()
                        .setVariable("id",json.getId())
                        .setVariable( "latitude",json.getLatitude())
                        .setVariable("longitude",json.getLongitude())
                        .setVariable( "location_id",json.getLocationId());

                double evaluate = exp.evaluate();
                results.add(evaluate);

            }


    }
        return results;
    }

//    public List<Double> calculate(List<String> columns,List<JsonModel> jsonData) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
//
//
//        List<Double> results = new ArrayList<>();
//            for(JsonModel json:jsonData){
//            for(String expression:columns){
//                String[] columnToCalculate = expression.split("[-+*/]");
//                for(String col:columnToCalculate){
//                    expression = expression.replace(col, String.valueOf(getColumnValue(json, col)));
//                }
//
//                Expression exp = new ExpressionBuilder(expression).build();
//                double evaluate = exp.evaluate();
//                results.add(evaluate);
//
//            }
//
//
//    }
//        return results;
//    }

}
