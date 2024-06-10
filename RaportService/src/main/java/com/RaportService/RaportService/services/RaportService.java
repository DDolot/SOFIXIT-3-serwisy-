package com.RaportService.RaportService.services;

import com.RaportService.RaportService.clients.SecondServiceClient;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RaportService {

    private SecondServiceClient secondServiceClient;

    public RaportService(SecondServiceClient secondServiceClient) {
        this.secondServiceClient = secondServiceClient;
    }

    public Map<String,List<String>> responseHandler(ResponseEntity<?> response){
        Map<String,List<String>> map = new HashMap<>();
        HttpHeaders headers = response.getHeaders();

        List<String> measurementForFirstService = headers.get("MeasurementForFirstService");
        List<String> measurementForSecondService = headers.get("MeasurementForSecondService");

        map.put("MeasurementForFirstService",measurementForFirstService);
        map.put("MeasurementForSecondService",measurementForSecondService);

        return map;
    }



}
