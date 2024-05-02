package org.example.clearsolutions.dto;

import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserUpdateDto {
    @Email(message = "Incorrect email format")
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
