package com.mlbyl.usermanagementservicetask;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlbyl.usermanagementservicetask.controller.UserController;
import com.mlbyl.usermanagementservicetask.dto.UserCreateRequest;
import com.mlbyl.usermanagementservicetask.dto.UserResponse;
import com.mlbyl.usermanagementservicetask.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    void createUser_Success() throws Exception {
        // Given
        UserCreateRequest request = new UserCreateRequest();
        request.setName("John");
        request.setSurname("Doe");
        request.setEmail("john@example.com");
        request.setPassword("password123");
        request.setPhoneNumber("+1234567890");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));

        UserResponse response = new UserResponse();
        response.setName("John");
        response.setEmail("john@example.com");

        when(userService.createUser(any(UserCreateRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("John"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));
    }

    @Test
    void getUserById_Success() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        UserResponse response = new UserResponse();
        response.setName("John");
        response.setEmail("john@example.com");

        when(userService.getUserById(userId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("John"));
    }

    @Test
    void deleteUserById_Success() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();

        // When & Then
        mockMvc.perform(delete("/api/user/{userId}", userId))
                .andExpect(status().isOk());
    }
}