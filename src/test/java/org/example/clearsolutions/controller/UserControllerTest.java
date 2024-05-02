package org.example.clearsolutions.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import org.example.clearsolutions.dto.UserRequestDto;
import org.example.clearsolutions.dto.UserResponseDto;
import org.example.clearsolutions.dto.UserUpdateDto;
import org.example.clearsolutions.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    protected static MockMvc mockMvc;
    private static UserRequestDto userRequestDto;
    private static UserRequestDto updateUserRequestDto;
    private static UserResponseDto userResponseDto;
    private static UserResponseDto updatedUserResponseDto;
    private static UserUpdateDto userUpdateDto;
    @Autowired
    private UserController userController;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        userRequestDto = new UserRequestDto()
                .setEmail("bob@example.com")
                .setFirstName("Bob")
                .setLastName("Johnson")
                .setBirthDate(LocalDate.of(2000, Month.MARCH, 28));

        updateUserRequestDto = new UserRequestDto()
                .setEmail("bob@example.com")
                .setFirstName("John")
                .setLastName("Johnson")
                .setBirthDate(LocalDate.of(2000, Month.MARCH, 28));

        userResponseDto = new UserResponseDto()
                .setId(1L)
                .setEmail("bob@example.com")
                .setFirstName("Bob")
                .setLastName("Johnson")
                .setBirthDate(LocalDate.of(2000, Month.MARCH, 28));

        updatedUserResponseDto = new UserResponseDto()
                .setId(1L)
                .setEmail("bob@example.com")
                .setFirstName("John")
                .setLastName("Johnson")
                .setBirthDate(LocalDate.of(2000, Month.MARCH, 28));

        userUpdateDto = new UserUpdateDto()
                .setFirstName("John");
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void register_ValidRequest_Success() throws Exception {
        Mockito.when(userService.register(any()))
                .thenReturn(userResponseDto);

        MvcResult mvcResult = mockMvc.perform(
                post("/users/registration")
                        .content(objectMapper.writeValueAsBytes(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDto expected = userResponseDto;
        UserResponseDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                UserResponseDto.class
        );

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updatePartially_ValidRequest_Success() throws Exception {
        Long id = 1L;

        Mockito.when(userService.updatePartially(any(), any()))
                .thenReturn(updatedUserResponseDto);

        MvcResult mvcResult = mockMvc.perform(
                patch("/users/{id}", id)
                        .content(objectMapper.writeValueAsBytes(userUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDto expected = updatedUserResponseDto;
        UserResponseDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                UserResponseDto.class
        );

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateCompletely_ValidRequest_Success() throws Exception {
        Long id = 1L;

        Mockito.when(userService.updateCompletely(any(), any()))
                .thenReturn(updatedUserResponseDto);

        MvcResult mvcResult = mockMvc.perform(
                put("/users/{id}", id)
                        .content(objectMapper.writeValueAsBytes(updateUserRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserResponseDto expected = updatedUserResponseDto;
        UserResponseDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                UserResponseDto.class
        );

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete_ValidRequest_Success() throws Exception {
        Long id = 1L;

        mockMvc.perform(
                delete("/users/{id}", id)
        ).andExpect(status().isNoContent());
    }

    @Test
    void getByDateBetween_ValidRequest_Success() throws Exception {
        LocalDate dateFrom = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDate dateTo = LocalDate.of(2006, Month.JANUARY, 1);

        Mockito.when(userService.getByDateBetween(any(), any()))
                .thenReturn(List.of(userResponseDto));

        MvcResult mvcResult = mockMvc.perform(
                get("/users")
                        .param("from", String.valueOf(dateFrom))
                        .param("to", String.valueOf(dateTo))
                )
                .andExpect(status().isOk())
                .andReturn();

        List<UserResponseDto> expected = List.of(userResponseDto);
        UserResponseDto[] actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsByteArray(),
                UserResponseDto[].class
        );

        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }
}
