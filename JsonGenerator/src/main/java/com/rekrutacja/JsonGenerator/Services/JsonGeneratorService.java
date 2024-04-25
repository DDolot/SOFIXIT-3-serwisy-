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
        PositionDTO position = PositionDTO.builder()
                ._type("Position")
                .id(random.nextInt(100000000))
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
                .locationId(random.nextInt(1_000_000))
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

    }public String[] generateJsonList(int size) {
        return Arrays.stream(new String[size])
                .parallel()
                .map(i -> generateJson())
                .toArray(String[]::new);
    }
    public float generateRandomCoordinate(float min, float max) {
        return min + (max - min) * random.nextFloat();
    }
}
