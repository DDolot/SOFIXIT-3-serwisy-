package com.RaportService.RaportService.services;

import com.RaportService.RaportService.clients.SecondServiceClient;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;


import java.lang.management.ManagementFactory;

@Service
public class RaportService {

    SecondServiceClient secondServiceClient;

    public RaportService(SecondServiceClient secondServiceClient) {
        this.secondServiceClient = secondServiceClient;
    }



}
