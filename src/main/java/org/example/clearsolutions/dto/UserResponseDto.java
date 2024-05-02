package org.example.clearsolutions.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
