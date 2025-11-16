package com.mlbyl.usermanagementservicetask.mapper;

import com.mlbyl.usermanagementservicetask.dto.UserCreateRequest;
import com.mlbyl.usermanagementservicetask.dto.UserResponse;
import com.mlbyl.usermanagementservicetask.dto.UserUpdateRequest;
import com.mlbyl.usermanagementservicetask.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static User toEntity(UserCreateRequest request) {
        return User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(request.getPassword())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public static User updateEntity(UserUpdateRequest request, User user) {
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());

        return user;
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getDateOfBirth(),
                user.getUserRole()
        );
    }

    public static List<UserResponse> toResponse(List<User> users) {
        if (users == null) {
            return Collections.emptyList();
        }

        return users.stream().map(u -> toResponse(u)).collect(Collectors.toList());

    }

}
