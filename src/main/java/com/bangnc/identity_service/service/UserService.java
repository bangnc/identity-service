package com.bangnc.identity_service.service;

import com.bangnc.identity_service.dto.request.UserCreationRequest;
import com.bangnc.identity_service.dto.request.UserUpdateRequest;
import com.bangnc.identity_service.dto.response.UserResponse;
import com.bangnc.identity_service.entity.User;
import com.bangnc.identity_service.enums.Role;
import com.bangnc.identity_service.exception.AppException;
import com.bangnc.identity_service.exception.ErrorCode;
import com.bangnc.identity_service.mapper.UserMapper;
import com.bangnc.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createRequest(UserCreationRequest request) {
        User user = new User();
        if (userRepository.existsByUserName(request.getUserName()))
            throw new AppException(ErrorCode.USER_EXISTED);
        user = userMapper.toUser(request);
        user.setPassWord(passwordEncoder.encode(request.getPassWord()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUser(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public String deleteUser(String userId) {
        userRepository.deleteById(userId);
        return "User has been deleted";
    }
}
