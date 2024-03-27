package com.rekrutacja.SecondService.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rekrutacja.SecondService.Models.JsonModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonModelDTO {


    private Integer id;
    private Double latitude;
    private Double longitude;
    private Integer location_id;

}
