package com.mlbyl.usermanagementservicetask.service;

import com.mlbyl.usermanagementservicetask.dto.UserCreateRequest;
import com.mlbyl.usermanagementservicetask.dto.UserResponse;
import com.mlbyl.usermanagementservicetask.dto.UserUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);

    UserResponse getUserById(UUID userId);

    List<UserResponse> getAll();

    UserResponse udpateUser(UserUpdateRequest request, UUID userId);

    void deleteUserById(UUID userId);

}
