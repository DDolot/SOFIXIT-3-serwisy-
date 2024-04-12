package com.rekrutacja.FirstService.Services;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import com.rekrutacja.FirstService.Models.DTO;
import com.rekrutacja.FirstService.Models.GeoPositionDTO;
import com.rekrutacja.FirstService.Models.PositionDTO;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FirstService {
    private final Faker faker = new Faker();
    private final Random random = new Random();
    PerformanceMonitor pm = new PerformanceMonitor();

    public String generateJson() {
        PositionDTO position = PositionDTO.builder()
                ._type("Position")
                .id(random.nextInt(100000))
                .key(null)
                .name(faker.name().fullName())
                .fullName(faker.name().fullName() + " " + "Poland")
                .airportCode(null)
                .type("location")
                .country("Poland")
                .geoPosition(
                        new GeoPositionDTO(
                        generateRandomCoordinate(-90,90),
                        generateRandomCoordinate(-180,180)
                ))
                .locationId(10000)
                .inEurope(true)
                .countryCode("PL")
                .coreCountry(true)
                .distance(null)
                .build();
        try {
            return new ObjectMapper().writeValueAsString(position);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public String[] generateJsonList(int size) {

         String[] jsonList = new String[size];

        for (int i = 0; i < size; i++) {

            jsonList[i] = (generateJson());
        }
        return jsonList;
    }



    private static double generateRandomCoordinate(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }




}
