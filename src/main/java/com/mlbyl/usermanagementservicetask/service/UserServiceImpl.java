package com.mlbyl.usermanagementservicetask.service;

import com.mlbyl.usermanagementservicetask.dto.UserCreateRequest;
import com.mlbyl.usermanagementservicetask.dto.UserResponse;
import com.mlbyl.usermanagementservicetask.dto.UserUpdateRequest;
import com.mlbyl.usermanagementservicetask.entity.User;
import com.mlbyl.usermanagementservicetask.enums.UserRole;
import com.mlbyl.usermanagementservicetask.exception.BusinessException;
import com.mlbyl.usermanagementservicetask.exception.NotFoundException;
import com.mlbyl.usermanagementservicetask.mapper.UserMapper;
import com.mlbyl.usermanagementservicetask.repository.UserRepository;
import com.mlbyl.usermanagementservicetask.utils.constant.ErrorCode;
import com.mlbyl.usermanagementservicetask.utils.constant.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        checkEmailExist(request.getEmail());

        User user = UserMapper.toEntity(request);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUserRole(UserRole.ROLE_USER);

        User savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_WITH_ID.format(userId),
                        ErrorCode.USER_NOT_FOUND.name()));

        return UserMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new NotFoundException(ErrorMessage.NO_USERS_FOUND_IN_DATABASE.getMessage(),
                    ErrorCode.USER_NOT_FOUND.name());
        }

        return UserMapper.toResponse(allUsers);
    }

    @Override
    public UserResponse udpateUser(UserUpdateRequest request, UUID userId) {
        User existUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_WITH_ID.format(userId),
                        ErrorCode.USER_NOT_FOUND.name()));

        if (!existUser.getEmail().equals(request.getEmail())) {
            checkEmailExist(request.getEmail());
        }

        User updatedUser = UserMapper.updateEntity(request, existUser);
        User savedUser = userRepository.save(updatedUser);

        return UserMapper.toResponse(savedUser);
    }

    @Override
    public void deleteUserById(UUID userId) {
        User existUser = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_WITH_ID.format(userId),
                        ErrorCode.USER_NOT_FOUND.name()));

        userRepository.deleteById(userId);
    }

    private void checkEmailExist(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorMessage.EMAIL_ALREADY_TAKEN.getMessage(),
                    ErrorCode.USER_EXIST_EMAIL.name(),
                    HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
        }
    }

}


