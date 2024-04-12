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

   public long xD(){
       long startTime = System.currentTimeMillis();
       // to jest pobranie danych z 2 serwisu , ale przecież drugi serwis musi
       // pobrać je z pierwszego, więc czas jaki dostaje to 3 -> 1 , nie wiem
       // ile jest 3 -> 2 -> 1
       secondServiceClient.fetchConstantData();
       long endTime = System.currentTimeMillis();
       long requestTime = endTime - startTime;
        return requestTime;

   }
   // pozatym nie wiem jak zmierzyć cpu i memory dla każdego z serwisu osobo ?

    public String generatePerformanceReport() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        secondServiceClient.fetchConstantData();





        // Get CPU and memory usage for each service

        double cpuUsageService1 = osBean.getProcessCpuLoad();


        return String.format("CPU load is %.2f%%", cpuUsageService1 * 100);

    }
}
