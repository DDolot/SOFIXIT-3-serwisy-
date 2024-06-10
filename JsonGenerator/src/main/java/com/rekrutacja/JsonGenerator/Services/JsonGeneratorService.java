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
    private static final Faker faker = new Faker();
    private final ObjectMapper objectmapper = new ObjectMapper();

    public String generateJson() {
        PositionDTO position = PositionDTO.builder()
                ._type("Position")
                .id(faker.random().nextInt(10000000))
                .key(null)
                .name(faker.name().fullName())
                .airportCode(null)
                .type("location")
                .country("Poland")
                .geoPosition(
                        new GeoPositionDTO(
                        generateRandomCoordinate(-90,90),
                        generateRandomCoordinate(-180,180)
                ))
                .locationId(faker.random().nextInt(100000))
                .inEurope(faker.random().nextBoolean())
                .countryCode("PL")
                .coreCountry(faker.random().nextBoolean())
                .distance(null)
                .build();
        try {
            return objectmapper.writeValueAsString(position);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public String[] generateJsonList(int size) {

        return Arrays.stream(new String[size])
                .parallel()
                .map(json -> generateJson())
                .toArray(String[]::new);
    }
     public double generateRandomCoordinate(double min, double max) {
        return min + (max - min) * faker.random().nextDouble();
    }
}
