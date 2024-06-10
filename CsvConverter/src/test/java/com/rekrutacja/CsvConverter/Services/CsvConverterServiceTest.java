package com.rekrutacja.CsvConverter.Services;

import com.rekrutacja.CsvConverter.DTOs.GeoPositionDTO;
import com.rekrutacja.CsvConverter.DTOs.PositionDTO;
import com.rekrutacja.CsvConverter.clients.JsonGeneratorServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CsvConverterServiceTest {
    private final CsvConverterService csvConverterService;
    @Autowired
    CsvConverterServiceTest(CsvConverterService csvConverterService, JsonGeneratorServiceClient mockedJsonClient) {
        this.csvConverterService = csvConverterService;
    }

    @BeforeEach
    public void setUp() {
        List<String> jsons = List.of("{\"_type\":\"Position\",\"id\":25,\"key\":null,\"name\":\"Palmer Hackett\",\"fullName\":\"Palmer Hackett Poland\",\"airportCode\":null,\"type\":\"location\",\"country\":\"Poland\",\"geoPosition\":{\"latitude\":100.0,\"longitude\":64.0},\"locationId\":36,\"inEurope\":true,\"countryCode\":\"PL\",\"coreCountry\":true,\"distance\":null}");
        PositionDTO position = PositionDTO.builder()
                ._type("Position")
                .id(25)
                .key(null)
                .name("Palmer Hackett")
                .fullName("Palmer Hackett Poland").build();


    }

    @Test
    public void ShouldAddCorrectly() {
        String[] operations = new String[]{"id+id","location_id+id","latitude+longitude","latitude+location_id"};
        double[] actuallResult = csvConverterService.calculate(operations);
        double[] expectedResult = new double[]{50,61,164.0,136.0};

        assertArrayEquals(expectedResult, actuallResult);

    }
    @Test
    public void ShouldSubstractCorrectly() {
        String[] operations = new String[]{"id-id","location_id-id","latitude-longitude","latitude-location_id"};
        double[] actuallResult = csvConverterService.calculate(operations);
        double[] expectedResult = new double[]{0,11,36,64};

        assertArrayEquals(expectedResult, actuallResult);

    }
    @Test
    public void ShouldMultiplyCorrectly() {
        String[] operations = new String[]{"id*id","location_id*id","latitude*longitude","latitude*location_id"};
        double[] actuallResult = csvConverterService.calculate(operations);
        double[] expectedResult = new double[]{625,900,6400,3600};

        assertArrayEquals(expectedResult, actuallResult);

    }
    @Test
    public void ShouldDivideCorrectly() {
        String[] operations = new String[]{"id/id","location_id/id","latitude/longitude","latitude/location_id"};
        double[] actuallResult = csvConverterService.calculate(operations);
        double[] expectedResult = new double[]{1,1.44,1.5625,2.7777777777777777777777777777778};

        assertArrayEquals(expectedResult, actuallResult);
    }
    @Test
    public void DivisionByZeroCase() {
        String[] operations = new String[]{"id/0"};
        assertThrows(ArithmeticException.class, () -> {
            csvConverterService.calculate(operations);
        });
    }
    @Test
    public void ShouldExponentCorrectly() {
        String[] operations = new String[]{"id^2","latitude^2"};
        double[] actuallResult = csvConverterService.calculate(operations);
        double[] expectedResult = new double[]{625,10000};

        assertArrayEquals(expectedResult, actuallResult);

    }
    @Test
    public void ShouldSquareCorrectly() {
        String[] operations = new String[]{"sqrt(id)","sqrt(latitude)","sqrt(location_id)"};
        double[] actuallResult = csvConverterService.calculate(operations);
        double[] expectedResult = new double[]{5,10,6};

        assertArrayEquals(expectedResult, actuallResult);

    }
    @Test
    public void ShouldMakeComplexOperationCorrectly() {
        String[] operations = new String[]{"sqrt(id)*id^2-20+sqrt(latitude)/10"};
        double[] actuallResult = csvConverterService.calculate(operations);
        double[] expectedResult = new double[]{3106};

        assertArrayEquals(expectedResult, actuallResult);

    }

    @Test
    public void ShouldMakeCorrectString() {
        String[] columns = new String[]{"id","name","fullName","key"};
        String actuallResult = csvConverterService.convertToCSV(columns);

        String expectedResult = "25,Palmer Hackett,Palmer Hackett Poland,null\n";
        assertThat(actuallResult).isEqualTo(expectedResult);

    }



    }



