package org.example.clearsolutions.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.clearsolutions.dto.UserRequestDto;
import org.example.clearsolutions.dto.UserResponseDto;
import org.example.clearsolutions.dto.UserUpdateDto;
import org.example.clearsolutions.exception.EntityNotFoundException;
import org.example.clearsolutions.exception.RegistrationException;
import org.example.clearsolutions.exception.UnderageException;
import org.example.clearsolutions.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponseDto register(
            @RequestBody @Valid UserRequestDto userRequestDto
    ) throws UnderageException, RegistrationException {
        return userService.register(userRequestDto);
    }

    @PatchMapping("/{id}")
    public UserResponseDto updatePartially(
            @PathVariable Long id,
            @RequestBody UserUpdateDto userUpdateDto
    ) throws IllegalAccessException, UnderageException, EntityNotFoundException {
        return userService.updatePartially(id, userUpdateDto);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateCompletely(
            @PathVariable Long id,
            @RequestBody @Valid UserRequestDto userRequestDto
    ) throws UnderageException, EntityNotFoundException {
        return userService.updateCompletely(id, userRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public List<UserResponseDto> getByDateBetween(
            @RequestParam("from") LocalDate dateFrom,
            @RequestParam("to") LocalDate dateTo
    ) {
        return userService.getByDateBetween(dateFrom, dateTo);
    }
}
