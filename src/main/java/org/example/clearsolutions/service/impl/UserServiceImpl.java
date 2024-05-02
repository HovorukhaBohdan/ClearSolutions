package org.example.clearsolutions.service.impl;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.clearsolutions.dto.UserRequestDto;
import org.example.clearsolutions.dto.UserResponseDto;
import org.example.clearsolutions.dto.UserUpdateDto;
import org.example.clearsolutions.exception.EntityNotFoundException;
import org.example.clearsolutions.exception.RegistrationException;
import org.example.clearsolutions.exception.UnderageException;
import org.example.clearsolutions.mapper.UserMapper;
import org.example.clearsolutions.model.User;
import org.example.clearsolutions.repository.UserRepository;
import org.example.clearsolutions.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${user.age}")
    private int minimalAge;

    @Override
    public UserResponseDto register(UserRequestDto userRequestDto)
            throws UnderageException, RegistrationException {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException(
                    String.format("User with email: %s is already exists",
                            userRequestDto.getEmail())
            );
        }

        User user = userMapper.toModel(userRequestDto);
        validateAge(user.getBirthDate());

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updatePartially(Long id, UserUpdateDto userUpdateDto)
            throws IllegalAccessException, EntityNotFoundException, UnderageException {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find a user with id: " + id)
        );

        Class<?> userClass = user.getClass();
        Class<?> userUpdateDtoClass = userUpdateDto.getClass();
        Field[] userFields = userClass.getDeclaredFields();
        Field[] userUpdateDtoFields = userUpdateDtoClass.getDeclaredFields();

        for (Field userField : userFields) {
            userField.setAccessible(true);

            for (Field userUpdateDtoField : userUpdateDtoFields) {
                userUpdateDtoField.setAccessible(true);
                Object value = userUpdateDtoField.get(userUpdateDto);

                if (userUpdateDtoField.getName().equals("birthDate")) {
                    validateAge(user.getBirthDate());
                }

                if (value != null && userField.getName().equals(userUpdateDtoField.getName())) {
                    userField.set(user, value);
                }
            }
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateCompletely(Long id, UserRequestDto userRequestDto)
            throws EntityNotFoundException, UnderageException {
        userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find a user with id: " + id)
        );

        User updatedUser = userMapper.toModel(userRequestDto);
        validateAge(updatedUser.getBirthDate());
        updatedUser.setId(id);

        return userMapper.toDto(userRepository.save(updatedUser));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponseDto> getByDateBetween(LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException(
                    String.format("Date from (%s) can't be after date to (%s)", dateFrom, dateTo)
            );
        }

        List<User> users = userRepository.getAllByBirthDateBetween(dateFrom, dateTo);

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    private void validateAge(LocalDate birthDate) throws UnderageException {
        LocalDate currentDate = LocalDate.now();
        int usersAge = Period.between(birthDate, currentDate).getYears();

        if (usersAge < minimalAge) {
            throw new UnderageException(String.format(
                    "Users age: %d is less than minimal age: %d", usersAge, minimalAge
            ));
        }
    }
}
