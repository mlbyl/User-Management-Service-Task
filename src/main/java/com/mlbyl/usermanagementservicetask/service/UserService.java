package com.mlbyl.usermanagementservicetask.service;

import com.mlbyl.usermanagementservicetask.dto.*;

import java.util.UUID;

public interface UserService {
    UserResponse createUser(UserCreateRequest request);

    UserResponse getUserById(UUID userId);

    PageResponse<UserResponse> getAll(UserFilterRequest request, int page, int size);

    UserResponse udpateUser(UserUpdateRequest request, UUID userId);

    void deleteUserById(UUID userId);

}
