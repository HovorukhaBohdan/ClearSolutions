package org.example.clearsolutions.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class UserRequestDto {
    private static final String NOT_EMPTY = "Can't be null or empty";

    @Email(message = "Incorrect email format")
    @NotEmpty(message = NOT_EMPTY)
    private String email;
    @NotEmpty(message = NOT_EMPTY)
    private String firstName;
    @NotEmpty(message = NOT_EMPTY)
    private String lastName;
    @NotNull(message = "Can't be null")
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
