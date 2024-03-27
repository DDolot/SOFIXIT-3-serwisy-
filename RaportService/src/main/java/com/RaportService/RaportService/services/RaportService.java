package com.RaportService.RaportService.services;

import com.RaportService.RaportService.clients.SecondServiceClient;
import org.springframework.stereotype.Service;

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

}
