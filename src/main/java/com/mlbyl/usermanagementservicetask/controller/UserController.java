package com.mlbyl.usermanagementservicetask.controller;

import com.mlbyl.usermanagementservicetask.dto.*;
import com.mlbyl.usermanagementservicetask.service.UserService;
import com.mlbyl.usermanagementservicetask.utils.Result.Result;
import com.mlbyl.usermanagementservicetask.utils.constant.SuccessMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<Result<UserResponse>> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserResponse userResponse = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.success(userResponse, SuccessMessage.USER_CREATED_SUCCESSFULLY.getMessage()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Result<UserResponse>> getUserById(@PathVariable UUID userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(
                Result.success(userResponse, SuccessMessage.USER_RETRIEVED_SUCCESSFULLY.getMessage()));
    }

    @GetMapping()
    public ResponseEntity<Result<PageResponse<UserResponse>>> getAll(UserFilterRequest filterRequest,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size) {
        PageResponse<UserResponse> userResponse = userService.getAll(filterRequest, page, size);
        return ResponseEntity.ok(
                Result.success(userResponse, SuccessMessage.ALL_USERS_RETRIEVED_SUCCESSFULLY.getMessage()));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Result<UserResponse>> updateUser(@Valid @RequestBody UserUpdateRequest request,
                                                           @PathVariable UUID userId) {
        UserResponse userResponse = userService.udpateUser(request, userId);
        return ResponseEntity.ok(
                Result.success(userResponse, SuccessMessage.USER_UPDATED_SUCCESSFULLY.getMessage()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Result<?>> deleteUserById(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok(Result.success(SuccessMessage.USER_DELETED_SUCCESSFULLY.getMessage()));
    }

}
