package com.bangnc.identity_service.controller;

import com.bangnc.identity_service.dto.request.ApiResponse;
import com.bangnc.identity_service.dto.request.UserCreationRequest;
import com.bangnc.identity_service.dto.request.UserUpdateRequest;
import com.bangnc.identity_service.dto.response.UserResponse;
import com.bangnc.identity_service.entity.User;
import com.bangnc.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createRequest(request));
        return apiResponse;
    }
    @GetMapping()
    List<User> getUsers () {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("UserName: {}", authentication.getName());
        var x = authentication.getAuthorities();
        authentication.getAuthorities()
                .forEach(a -> log.info(a.getAuthority().toUpperCase()));
        return userService.getUsers();

    }
    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId,request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }
}
