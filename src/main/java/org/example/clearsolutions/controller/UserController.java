package org.example.clearsolutions.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.clearsolutions.dto.UserRequestDto;
import org.example.clearsolutions.dto.UserResponseDto;
import org.example.clearsolutions.dto.UserUpdateDto;
import org.example.clearsolutions.exception.EntityNotFoundException;
import org.example.clearsolutions.exception.RegistrationException;
import org.example.clearsolutions.exception.UnderageException;
import org.example.clearsolutions.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserResponseDto register(
            @RequestBody @Valid UserRequestDto userRequestDto
    ) throws UnderageException, RegistrationException {
        return userService.register(userRequestDto);
    }

    @PatchMapping("/{id}")
    public UserResponseDto updatePartially(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateDto userUpdateDto
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
