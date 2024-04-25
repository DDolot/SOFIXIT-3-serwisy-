package com.rekrutacja.CsvConverter.Services;

import com.rekrutacja.CsvConverter.DTOs.GeoPositionDTO;
import com.rekrutacja.CsvConverter.DTOs.PositionDTO;
import com.rekrutacja.CsvConverter.clients.JsonGeneratorServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CsvConverterServiceTest {

    @Autowired
    private CsvConverterService csvConverterService;
    private PositionDTO position;
    @BeforeEach
    public void setUp() {
        position = PositionDTO.builder()
                .id(10)
                .locationId(20)
                .geoPosition(new GeoPositionDTO(
                        10.0F, 15.0F
                ))
                .build();

    }

    @Test
    public void ShouldAddCorrectly(){

        CsvConverterService mockedCsvService = mock(CsvConverterService.class);
        List<PositionDTO> lista = List.of(position);
        when(mockedCsvService.fetchData()).thenReturn(lista);


        double[] calculate = csvConverterService.calculate(new String[]{"id+id"}, mockedCsvService.fetchData());


    }


    }



