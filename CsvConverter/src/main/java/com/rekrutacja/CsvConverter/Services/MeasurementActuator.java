package com.rekrutacja.CsvConverter.Services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rekrutacja.CsvConverter.DTOs.MeasurementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class MeasurementActuator {


    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    public static final Logger logger  = LoggerFactory.getLogger(MeasurementActuator.class);
    public MeasurementActuator(RestTemplate restTemplate,ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public MeasurementDTO takeMeasurement(CompletableFuture<Void> future){
        logger.info("jestem asynchorniczy !");
        List<Double> cpuLoads = new ArrayList<>();
        List<Double> memory = new ArrayList<>();

        boolean measure = true;

        while (measure) {

            cpuLoads.add(getCPUUsage());
            memory.add(Double.valueOf(getMemoryUsageInMB()));

            if (future.isDone()) {
                measure = false;
            }
            try {
                Thread.sleep(10);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        future.join();

return null;
//        return new MeasurementDTO(cpuLoads,memory,0);
    }

    public double getMemoryUsageInMB() {
        String url = "http://localhost:8081/actuator/metrics/jvm.memory.used";
        String response = restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();



        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode measurements = root.get("measurements");

                double valueInBytes = measurements.get(0).get("value").asDouble();
                return valueInBytes / (1024 * 1024); // Convert bytes to megabytes

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0.0;
    }
    public double getCPUUsage() {
        String url = "http://localhost:8081/actuator/metrics/system.cpu.usage";
        String response = restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();



        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode measurements = root.get("measurements");

                double valueInBytes = measurements.get(0).get("value").asDouble();
                return valueInBytes * 100; // Convert bytes to megabytes

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}
