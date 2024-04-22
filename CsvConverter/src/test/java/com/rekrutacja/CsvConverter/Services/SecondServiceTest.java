package com.rekrutacja.CsvConverter.Services;

import com.rekrutacja.CsvConverter.DTOs.PositionDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecondServiceTest {

    @Autowired
    private CsvConverterService secondServiceDifferent;

    @BeforeEach
    public void setUp() {

    }

@Test
    void ThirdEndPointShouldReturnCorrectValue(){

        //given
        CsvConverterService mockedService = Mockito.mock(CsvConverterService.class);
        PositionDTO json = new PositionDTO();
        json.setId(65483214);
        json.setGeoPosition(new PositionDTO.GeoPosition(51.0855422,16.9987442));
        json.setLocationId(756423);
    List<String> columns = List.of("latitude*longitude", "sqrt(location_id)");
    List<PositionDTO> jsonData = List.of(json);
    Mockito.when(mockedService.calculate(
            columns, jsonData)).thenReturn(List.of(3.0052538,869.7258188));

        List<Double> result = secondServiceDifferent.calculate(columns,jsonData);


        assertThat(result).isEqualTo(mockedService.calculate(columns,jsonData));

    }


    }



