package com.rekrutacja.JsonGenerator.Services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.rekrutacja.JsonGenerator.DTOs.GeoPositionDTO;
import com.rekrutacja.JsonGenerator.DTOs.PositionDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JsonGeneratorService {
    private final Faker faker = new Faker();
    private static final Random random = new Random();

    public String generateJson() {
        String name = faker.name().fullName();
        PositionDTO position = PositionDTO.builder()
                ._type("Position")
                .id(random.nextInt(100000))
                .key(null)
                .name(name)
                .fullName(name + " " + "Poland")
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
    private double generateRandomCoordinate(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
