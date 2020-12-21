package pl.polsl.staneczek.service.dto;

import pl.polsl.staneczek.model.Address;
import pl.polsl.staneczek.model.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize
public class UserDto {

    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;
    @JsonProperty
    private String confirmPassword;
    @JsonProperty
    private String phoneNumber;
    @JsonProperty
    private UserRole userRole;
    @JsonProperty
    private Address address;
}
