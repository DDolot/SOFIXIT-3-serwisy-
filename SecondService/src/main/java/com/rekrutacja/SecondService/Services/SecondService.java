package com.rekrutacja.SecondService.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.rekrutacja.SecondService.clients.firstServiceClient;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
@Aspect
public class SecondService {

    private firstServiceClient client;
    private  final String path  = "output.csv";

    public SecondService(firstServiceClient client) {
        this.client = client;
    }


    public List<String> fetchData() {
        return client.fetchJsonsFromFirstService();
    }


    public String  firstEP() throws IOException{
        List<String> jsons = fetchData();

        ObjectMapper om = new ObjectMapper();

        StringWriter csvData = new StringWriter();

        for(String json:jsons){
            JsonNode jsonNode = om.readTree(json);
            String row = String.join(",",
                    jsonNode.get("_type").asText(),
                    jsonNode.get("id").asText(),
                    jsonNode.get("name").asText(),
                    jsonNode.get("type").asText(),
                    jsonNode.get("geoPosition").get("latitude").asText(),
                    jsonNode.get("geoPosition").get("longitude").asText()
            );

            csvData.append(row).append("\n");

        }


        return csvData.toString();

    }

    public String secondEP(String[] params) throws IOException{
        List<String> jsons = fetchData();

        ObjectMapper om = new ObjectMapper();

        StringWriter csvData = new StringWriter();


        for (String json : jsons) {
            JsonNode jsonNode = om.readTree(json);

            StringBuilder row = new StringBuilder();

            for (String field : params) {
                String fieldValue;
                if (field.equals("latitude") || field.equals("longitude")) {
                    JsonNode geoPositionNode = jsonNode.get("geoPosition");
                    if (geoPositionNode != null) {
                        fieldValue = geoPositionNode.get(field).asText();
                    } else {
                        fieldValue = "";
                    }
                } else {
                    fieldValue = jsonNode.get(field).asText();
                }
                row.append(fieldValue).append(",");
            }
            row.deleteCharAt(row.length() - 1).append("\n");

            csvData.append(row);
        }

        return csvData.toString();
    }

















    public void ConvertToCsv(){

        ObjectMapper objectMapper = new ObjectMapper();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path)))
        {

        writer.write("_type,_id,key,name,fullName,iata_airport_code,type,country,latitude,longitude,location_id,inEurope,countryCode,coreCountry,distance");
        writer.newLine();

        for (String jsonString : fetchData()) {
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            StringBuilder line = new StringBuilder();
            Iterator<String> fieldNames = jsonNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode valueNode = jsonNode.get(fieldName);

                if (valueNode.isTextual()) {
                    line.append(valueNode.asText()).append(",");
                } else if (valueNode.isNumber()) {
                    line.append(valueNode.asDouble()).append(",");
                } else if (valueNode.isObject()) {
                    Iterator<String> nestedIterator = valueNode.fieldNames();
                    while (nestedIterator.hasNext()) {
                        String nestedFieldName = nestedIterator.next();
                        JsonNode nestedValueNode = valueNode.get(nestedFieldName);
                        line.append(nestedValueNode.asText()).append(",");
                    }
                }
            }

            writer.write(line.deleteCharAt(line.length() - 1).toString());
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public String xDDD(){
        ConvertToCsv();


        // Columns to read (indexes start from 0)
        int[] chosenColumns = {0, 2, 4,5,1,3,7}; // Example: reading columns 1, 3, and 5
        StringBuilder output = new StringBuilder();
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {

                for (int columnIndex : chosenColumns) {
                    if (columnIndex < nextLine.length) {
                        output.append(nextLine[columnIndex]).append(",");
                    }
                }
                // Remove the trailing comma and print the line
                output.substring(0, output.length() - 1);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return output.toString();
    }
    }





