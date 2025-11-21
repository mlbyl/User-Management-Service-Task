package com.mlbyl.usermanagementservicetask;


import com.mlbyl.usermanagementservicetask.dto.UserCreateRequest;
import com.mlbyl.usermanagementservicetask.dto.UserResponse;
import com.mlbyl.usermanagementservicetask.entity.User;
import com.mlbyl.usermanagementservicetask.exception.BusinessException;
import com.mlbyl.usermanagementservicetask.repository.UserRepository;
import com.mlbyl.usermanagementservicetask.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ObjectProvider<KafkaTemplate<String, String>> kafkaTemplate;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_Success() {
        UserCreateRequest request = new UserCreateRequest();
        request.setName("John");
        request.setSurname("Doe");
        request.setEmail("john@example.com");
        request.setPassword("password123");
        request.setPhoneNumber("+1234567890");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));

        User savedUser = new User();
        savedUser.setId(UUID.randomUUID());
        savedUser.setName("John");
        savedUser.setSurname("Doe");
        savedUser.setEmail("john@example.com");

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = userService.createUser(request);

        assertNotNull(response);
        assertEquals("John", response.getName());
        assertEquals("john@example.com", response.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_EmailExists_ThrowsException() {
        UserCreateRequest request = new UserCreateRequest();
        request.setEmail("existing@example.com");

        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

        assertThrows(BusinessException.class, () -> userService.createUser(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserById_Success() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setName("John");
        user.setEmail("john@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(userId);

        assertNotNull(response);
        assertEquals("John", response.getName());
    }

    @Test
    void deleteUserById_Success() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUserById(userId);

        verify(userRepository).deleteById(userId);
    }
}