package com.rekrutacja.SecondService.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.rekrutacja.SecondService.dtos.PositionDTO;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;


import java.io.*;

import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecondServiceTest {

    @Autowired
    private SecondService secondService;
    @Autowired
    private SecondServiceDifferent secondServiceDifferent;

    @BeforeEach
    public void setUp() {

    }

@Test
    void ThirdEndPointShouldReturnCorrectValue(){

        //given
        SecondServiceDifferent mockedService = Mockito.mock(SecondServiceDifferent.class);
        PositionDTO json = new PositionDTO();
        json.setId(65483214);
        json.setGeoPosition(new PositionDTO.GeoPosition(51.0855422,16.9987442));
        json.setLocationId(756423);
    List<String> columns = List.of("latitude*longitude", "sqrt(location_id)");
    List<PositionDTO> jsonData = List.of(json);
    Mockito.when(mockedService.calculate(
            columns, jsonData)).thenReturn(List.of(3.0052538,869.7258188));

        List<Double> result = secondServiceDifferent.calculate(columns,jsonData);


        assertThat(result).isEqualTo(mockedService.calculate(columns,jsonData));

    }




    @Test
    public void test() throws IOException {

        List<String> jsons = secondService.fetchData();
        ObjectMapper om = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        ArrayList<String> headers = new ArrayList<>();
        om.readTree(jsons.get(0)).fieldNames().forEachRemaining(headers::add);

        for (String json : jsons) {
            for (int i = 0; i < headers.size(); i++) {
                if (headers.get(i).equals("geoPosition")) {
                    JsonNode latitude = om.readTree(json).get(headers.get(i)).get("latitude");
                    JsonNode longitude = om.readTree(json).get(headers.get(i)).get("longitude");
                    stringWriter.append(longitude + ",");
                    stringWriter.append(latitude + ",");
                } else {
                    JsonNode jsonNode = om.readTree(json).get(headers.get(i));
                    stringWriter.append(jsonNode.asText() + ",");

                }


            }
            stringWriter.append("\n");
        }
        System.out.println(stringWriter);
    }


    @Test
    void xx() {
        ObjectMapper objectMapper = new ObjectMapper();

        List<String> jsonStrings = secondService.fetchData();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"))) {

            writer.write("_type,_id,key,name,fullName,iata_airport_code,type,country,latitude,longitude,location_id,inEurope,countryCode,coreCountry,distance");
            writer.newLine();

            for (String jsonString : jsonStrings) {
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


//    @Test
//    void tescik() throws IOException {
//
//        StringWriter sw = new StringWriter();
//
//        List<String> jsons = secondService.fetchData();
//        ObjectMapper om = new ObjectMapper();
//        JsonNode jsonNode = om.readTree(jsons.get(0));
//        System.out.println(jsonNode);
//
//        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
//
//        jsonNode.fieldNames().forEachRemaining(fieldName -> {
//            if (fieldName.equals("geo_position")) {
//                csvSchemaBuilder.addColumn("latitude");
//                csvSchemaBuilder.addColumn("longitude");
//            } else {
//                csvSchemaBuilder.addColumn(fieldName);
//            }
//        });
//
//
//        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
//        CsvMapper csvMapper = new CsvMapper();
//        csvMapper.writerFor(JsonNode.class)
//                .with(csvSchema)
//                .writeValueAsString(jsonNode);
//
//
//    }
//@Test
//    void t() throws IOException{
////    List<String> jsons = secondService.fetchData();
//
//
//
//    ObjectMapper mapper = new ObjectMapper()
//            // ignore JSON properties which are not mapped to POJO
//            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//
//    JsonNode jsonNode = mapper.readTree(jsons.get(0));
//
//    System.out.println(jsonNode);


    //}
//@Test
//public class r(){
// public int x = 5;
// public int y = 3;
// public int z = 25;
//
// public int[] math(String[] expression){
//     ...
//
//
//}
//// intput ["x*y","sqrt(z)"]
//    //output [15,5]
    @Test
    public void calculate() throws IOException{
        JsonNode jsonNode = new ObjectMapper().readTree(secondService.fetchData().get(0));

        int latitude = 5;
        int longitude = 5;
        int location_id = 10; // Assuming a value for location_id
        String x = "latitude-longitude*locationId";
        String[] strings = x.split("[-+*/]");
        System.out.println(jsonNode.get("geoPosition").get("latitude").asDouble());
        System.out.println(jsonNode.get("geoPosition").get("longitude").asDouble());
        System.out.println(jsonNode.get("locationId").asDouble());
        for (String s:strings){
            System.out.println(s);
        }
        char[] operators = x.replaceAll("[a-zA-Z0-9]", "").toCharArray();
        double result=0;
        double operand;
        for(char c : operators){
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals("latitude") || strings[i].equals("longitude")) {
                operand = jsonNode.get("geoPosition").get(strings[i]).asDouble();
            } else {
                operand = jsonNode.get(strings[i]).asDouble();
            }


            switch (c) {
                case '*':
                    result = result == 0.0 ? operand : result * operand; // Handle initial multiplication
                    break;
                case '/':
                    result = result == 0.0 ? operand : result / operand; // Handle initial division
                    break;
                case '+':
                    result = result == 0.0 ? operand : result + operand;
                    break;
                case '-':
                    result = result == 0.0 ? operand : result - operand;
                    break;
                default:
                    System.out.println("Invalid operator");
            }
        }
        }

        System.out.println("Result: " + result);
    }

    @Test
    void anothertest() throws IOException{
        String json = secondService.fetchData().get(0);

        ObjectMapper om = new ObjectMapper();

        PositionDTO jsonModel = om.readValue(json, PositionDTO.class);

        System.out.println(jsonModel);

        Double latitude = jsonModel.getLatitude();
        System.out.println(latitude);


    }

    @Test
    void v() {
        String expression = "sqrt(x)";
        Expression exp = new ExpressionBuilder(expression)
                .variables("x")
                .build()
                .setVariable("x", 25);

        System.out.println(exp.evaluate());

        String inputString = "sqrt(25)";

        // Regular expression pattern
        String patternString = "sqrt\\((.*?)\\)";

        // Creating a Pattern object
        Pattern pattern = Pattern.compile(patternString);

        // Creating a Matcher object
        Matcher matcher = pattern.matcher(inputString);

        // Using Matcher to find the value of x
        if (matcher.find()) {
            String x = matcher.group(1);
            System.out.println("Value of x: " + x);
        } else {
            System.out.println("No match found.");
        }
    }

    @Test
    void tt(){
        // Create a Runnable for fetchData
        Runnable fetchDataTask = () -> {
            // Call the fetchData method
            secondServiceDifferent.fetchData();
        };

// Create a new thread and pass the fetchDataTask to it
        Thread fetchDataThread = new Thread(fetchDataTask);
// Start the thread
        fetchDataThread.start();

// Rest of your code for measurements retrieval
        RestTemplate template = new RestTemplate();

        JsonNode cpuRequest = template.getForObject("http://localhost:8081/actuator/metrics/process.cpu.usage", JsonNode.class);
        double cpuValue = cpuRequest.get("measurements").get(0).get("value").asDouble();
        double cpuPercentage = cpuValue * 100;
        System.out.println("CPU Usage: " + cpuPercentage);

        JsonNode memoryRequest = template.getForObject("http://localhost:8081/actuator/metrics/jvm.memory.used", JsonNode.class);
        System.out.println("Memory Used: " + memoryRequest.get("measurements").get(0).get("value"));



    }


    }



