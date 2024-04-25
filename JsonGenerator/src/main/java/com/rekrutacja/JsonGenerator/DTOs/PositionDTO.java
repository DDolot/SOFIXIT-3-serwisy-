package com.rekrutacja.JsonGenerator.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonPropertyOrder({
        "_type",
        "id",
        "key",
        "name",
        "fullName",
        "airportCode",
        "type",
        "country",
        "geoPosition",
        "locationId",
        "inEurope",
        "countryCode",
        "coreCountry",
        "distance"
})

public class PositionDTO {
    @JsonProperty
    private String _type;
    @JsonProperty
    private long id;
    @JsonProperty
    private Boolean key;
    @JsonProperty
    private String name;

    @JsonProperty
    private Boolean airportCode;
    @JsonProperty
    private String type;
    @JsonProperty
    private String country;
    @JsonProperty
    private GeoPositionDTO geoPosition;
    @JsonProperty
    private long locationId;
    @JsonProperty
    private boolean inEurope;
    @JsonProperty
    private String countryCode;
    @JsonProperty
    private boolean coreCountry;
    @JsonProperty
    private Boolean distance;
    @JsonProperty("fullName")
    public String getFullName() {
        return name + " " + country;
    }
}







