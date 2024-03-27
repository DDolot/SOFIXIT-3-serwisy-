package com.rekrutacja.FirstService.Models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@JsonPropertyOrder
@AllArgsConstructor
@NoArgsConstructor

public class JsonModel {

    private String _type;

    private Integer id;

    private Boolean key;

    private String name;

    private String fullName;

    private String airportCode;

    private String type;

    private String country;

//    private Map<String, Double> geo_position;


    private GeoPosition geoPosition;
//    private Double latitude;
//    private Double longitude;
//

    private Integer locationId;


    private boolean inEurope;

    private String countryCode;

    private boolean coreCountry;

    private String distance;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GeoPosition {
        @JsonProperty("latitude")
        private Double latitude;
        @JsonProperty("longitude")
        private Double longitude;
    }

}







