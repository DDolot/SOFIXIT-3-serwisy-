package com.rekrutacja.FirstService.Models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@JsonPropertyOrder
@Builder

public class PositionDTO {

    private String _type;

    private int id;

    private Boolean key;

    private String name;

    private String fullName;

    private Boolean airportCode;

    private String type;

    private String country;

//    private Map<String, Double> geo_position;


    private GeoPositionDTO geoPosition;


    private int locationId;


    private boolean inEurope;

    private String countryCode;

    private boolean coreCountry;

    private String distance;



}







