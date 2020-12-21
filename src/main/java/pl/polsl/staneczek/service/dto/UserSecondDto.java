package pl.polsl.staneczek.service.dto;

import pl.polsl.staneczek.model.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize
public class UserSecondDto {

    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private Integer id;//id studenta lub insructora
    @JsonProperty
    private Address address;

    @JsonProperty
    private String email;
    @JsonProperty
    private String phoneNumber;

}
