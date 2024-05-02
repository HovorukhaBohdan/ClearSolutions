package org.example.clearsolutions.service;

import org.example.clearsolutions.dto.UserRequestDto;
import org.example.clearsolutions.dto.UserResponseDto;
import org.example.clearsolutions.dto.UserUpdateDto;
import org.example.clearsolutions.exception.EntityNotFoundException;
import org.example.clearsolutions.exception.RegistrationException;
import org.example.clearsolutions.exception.UnderageException;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserResponseDto register(UserRequestDto userRequestDto)
            throws UnderageException, RegistrationException;

    UserResponseDto updatePartially(Long id, UserUpdateDto userUpdateDto) throws IllegalAccessException, EntityNotFoundException, UnderageException;

    UserResponseDto updateCompletely(Long id, UserRequestDto userRequestDto) throws EntityNotFoundException, UnderageException;

    void deleteUser(Long id);

    List<UserResponseDto> getByDateBetween(LocalDate dateFrom, LocalDate dateTo);
}
