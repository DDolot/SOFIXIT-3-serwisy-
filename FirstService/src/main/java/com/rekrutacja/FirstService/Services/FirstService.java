package com.rekrutacja.FirstService.Services;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.javafaker.Faker;

import com.google.gson.JsonObject;
import com.rekrutacja.FirstService.Models.JsonModel;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FirstService {

    public String generateJson() {
        Faker faker = new Faker();
        Random random = new Random();
        JsonModel json = new JsonModel(
                "Position",
                random.nextInt(100000),
                null,
                faker.name().fullName(),
                faker.name().fullName() + " " + "Poland",
                null,
                "location",
                "Poland",
                new JsonModel.GeoPosition(generateRandomCoordinate(-90, 90), generateRandomCoordinate(-180, 180)),

                random.nextInt(100000),
                true,
                null,
                false,
                "distance"
        );


        try {
            return new ObjectMapper().writeValueAsString(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> generateJsonList(int size) {

         List<String> jsonList = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            jsonList.add(generateJson());
        }
        return jsonList;
//        return jsonList.stream()
//                .map(json -> json.replaceAll("[\"\\\\]", ""))
//                .toList();

    }



    private static double generateRandomCoordinate(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }


}
