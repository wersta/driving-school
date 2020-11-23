package com.example.demo.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize
public class AddressDto {
    @JsonProperty
    private String city;
    @JsonProperty
    private String postalCode;
    @JsonProperty
    private String street;
    @JsonProperty
    private int homeNumber;
}
