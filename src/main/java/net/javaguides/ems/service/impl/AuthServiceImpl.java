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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements net.javaguides.ems.service.AuthService {

    private final UserRepository userRepository;
    private final JwtServiceImpl jwtServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse loginUser(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtServiceImpl.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    @Transactional
    public AuthResponse registerUser(UserDto userDto) {

        User user = UserMapper.mapToUser(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(Role.HR);

        userRepository.save(user);

        String token = jwtServiceImpl.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
