package org.example.clearsolutions.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

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
