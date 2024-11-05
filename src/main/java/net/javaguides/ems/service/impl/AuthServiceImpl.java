package net.javaguides.ems.service.impl;

import lombok.RequiredArgsConstructor;
import net.javaguides.ems.auth.AuthResponse;
import net.javaguides.ems.auth.LoginRequest;
import net.javaguides.ems.dto.UserDto;
import net.javaguides.ems.entity.Role;
import net.javaguides.ems.entity.User;
import net.javaguides.ems.jwt.JwtServiceImpl;
import net.javaguides.ems.mapper.UserMapper;
import net.javaguides.ems.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements net.javaguides.ems.service.AuthService {

    private final UserRepository userRepository;
    private final JwtServiceImpl jwtServiceImpl;

    @Override
    public AuthResponse loginUser(LoginRequest request) {
        return null;
    }

    @Override
    @Transactional
    public AuthResponse registerUser(UserDto userDto) {

        User user = UserMapper.mapToUser(userDto);

        user.setRole(Role.HR);

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtServiceImpl.getToken(user)).build();
    }
}
