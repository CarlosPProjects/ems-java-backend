package net.javaguides.ems.service;

import net.javaguides.ems.auth.AuthResponse;
import net.javaguides.ems.auth.LoginRequest;
import net.javaguides.ems.dto.UserDto;

public interface AuthService {

    AuthResponse loginUser(LoginRequest request);
    AuthResponse registerUser(UserDto userDto);
}