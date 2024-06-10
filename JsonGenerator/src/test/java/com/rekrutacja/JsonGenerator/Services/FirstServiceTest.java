package com.rekrutacja.JsonGenerator.Services;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class FirstServiceTest {

    private JsonGeneratorService jsonGeneratorService;

    @BeforeEach
    public void setUp() {
        jsonGeneratorService = new JsonGeneratorService();
    }
    @Test
    @RepeatedTest(5)
    public void ShouldHaveProperStructureTest() throws JSONException {
        String randomJsonSample = jsonGeneratorService.generateJson();
        System.out.println(randomJsonSample);
        Pattern compiledPattern = Pattern.compile("\\{\"_type\":\"Position\",\"id\":\\d+,\"key\":null,\"name\":\"[\\w\\s.']+\",\"fullName\":\"[\\w\\s.']+\",\"airportCode\":null,\"type\":\"location\",\"country\":\"Poland\",\"geoPosition\":\\{\"latitude\":[+-]?\\d*\\.?\\d+,\"longitude\":[+-]?\\d*\\.?\\d+},\"locationId\":\\d+,\"inEurope\":(true|false),\"countryCode\":\"[A-Z]+\",\"coreCountry\":(true|false),\"distance\":null}");
        Matcher matcher = compiledPattern.matcher(randomJsonSample);

        assertTrue("json structure is incorrect",matcher.matches());

    }
    @Test
    public void ListShouldHaveProperSize() {
        int size = 101;
        String[] jsons = jsonGeneratorService.generateJsonList(size);
        assertEquals(jsons.length, size);
    }
    @RepeatedTest(25)
    @Test void ValuesShouldBeInBounds(){
        double coordinate = jsonGeneratorService.generateRandomCoordinate(-180, 180);

        assertThat(coordinate).isBetween(-180.0,180.0);

    }
    }

