package org.example.clearsolutions.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import org.example.clearsolutions.dto.UserRequestDto;
import org.example.clearsolutions.dto.UserResponseDto;
import org.example.clearsolutions.dto.UserUpdateDto;
import org.example.clearsolutions.exception.EntityNotFoundException;
import org.example.clearsolutions.exception.RegistrationException;
import org.example.clearsolutions.exception.UnderageException;
import org.example.clearsolutions.mapper.UserMapper;
import org.example.clearsolutions.model.User;
import org.example.clearsolutions.repository.UserRepository;
import org.example.clearsolutions.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static User user;
    private static UserRequestDto userRequestDto;
    private static UserResponseDto userResponseDto;
    private static UserResponseDto updatedUserResponseDto;
    private static UserUpdateDto userUpdateDto;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeAll
    static void beforeAll() {
        user = new User()
                .setEmail("bob@example.com")
                .setFirstName("Bob")
                .setLastName("Johnson")
                .setBirthDate(LocalDate.of(2004, Month.MARCH, 17));

        userRequestDto = new UserRequestDto()
                .setEmail("bob@example.com")
                .setFirstName("Bob")
                .setLastName("Johnson")
                .setBirthDate(LocalDate.of(2004, Month.MARCH, 17));

        userResponseDto = new UserResponseDto()
                .setEmail("bob@example.com")
                .setFirstName("Bob")
                .setLastName("Johnson")
                .setBirthDate(LocalDate.of(2004, Month.MARCH, 17));

        updatedUserResponseDto = new UserResponseDto()
                .setEmail("bob@example.com")
                .setFirstName("John")
                .setLastName("Johnson")
                .setBirthDate(LocalDate.of(2004, Month.MARCH, 17));

        userUpdateDto = new UserUpdateDto()
                .setFirstName("John");
    }

    @Test
    void register_validData_Success()
            throws RegistrationException, UnderageException {
        Mockito.when(userRepository.findByEmail(userRequestDto.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(userMapper.toModel(userRequestDto))
                .thenReturn(user);
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Mockito.when(userMapper.toDto(user))
                .thenReturn(userResponseDto);

        UserResponseDto expected = userResponseDto;
        UserResponseDto actual = userService.register(userRequestDto);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void register_usedEmail_RegistrationExceptionThrown()
            throws RegistrationException, UnderageException {
        Mockito.when(userRepository.findByEmail(userRequestDto.getEmail()))
                .thenReturn(Optional.of(user));

        Assertions.assertThrows(
                RegistrationException.class,
                () -> userService.register(userRequestDto)
        );
    }

    @Test
    void updatePartially_validData_Success()
            throws UnderageException, EntityNotFoundException, IllegalAccessException {
        Long id = 1L;

        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user))
                        .thenReturn(user);
        Mockito.when(userMapper.toDto(user))
                .thenReturn(updatedUserResponseDto);

        UserResponseDto expected = updatedUserResponseDto;
        UserResponseDto actual = userService.updatePartially(id, userUpdateDto);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updatePartially_invalidId_EntityNotFoundExceptionThrown() {
        Long id = 1L;

        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.updatePartially(id, userUpdateDto)
        );
    }

    @Test
    void updateCompletely_validData_Success()
            throws UnderageException, EntityNotFoundException {
        Long id = 1L;

        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.of(user));
        Mockito.when(userMapper.toModel(userRequestDto))
                .thenReturn(user);
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Mockito.when(userMapper.toDto(user))
                .thenReturn(updatedUserResponseDto);

        UserResponseDto expected = updatedUserResponseDto;
        UserResponseDto actual = userService.updateCompletely(id, userRequestDto);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateCompletely_invalidId_EntityNotFoundExceptionThrown() {
        Long id = 1L;

        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.updateCompletely(id, userRequestDto)
        );
    }

    @Test
    void getByDateBetween_validData_Success() {
        LocalDate from = LocalDate.of(2000, Month.MARCH, 1);
        LocalDate to = LocalDate.of(2008, Month.MARCH, 1);

        Mockito.when(userRepository.getAllByBirthDateBetween(from, to))
                .thenReturn(List.of(user));
        Mockito.when(userMapper.toDto(user))
                .thenReturn(userResponseDto);

        List<UserResponseDto> expected = List.of(userResponseDto);
        List<UserResponseDto> actual = userService.getByDateBetween(from, to);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByDateBetween_invalidDate_IllegalArgumentExceptionThrown() {
        LocalDate from = LocalDate.of(2008, Month.MARCH, 1);
        LocalDate to = LocalDate.of(2000, Month.MARCH, 1);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> userService.getByDateBetween(from, to)
        );
    }
}
