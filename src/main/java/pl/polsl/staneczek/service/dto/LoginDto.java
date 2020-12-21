package pl.polsl.staneczek.service.dto;

import pl.polsl.staneczek.model.UserRole;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize
public class LoginDto {

    private String token;
    private UserRole userRole;
}
