package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.UserRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.UserResponseDto;
import br.com.luiabdiel.smart_health_system.model.User;
import br.com.luiabdiel.smart_health_system.repository.UserRepository;
import br.com.luiabdiel.smart_health_system.shared.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        String encryptedPassword = this.passwordEncoder.encode(userRequestDto.getPassword());
        User user = new User(userRequestDto.getUsername(), encryptedPassword);

        var savedUser = this.userRepository.save(user);
        return UserResponseDto.fromEntity(savedUser);
    }

    public UserResponseDto findByUsername(String username) {
        User user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        return UserResponseDto.fromEntity(user);
    }

    public String login(UserRequestDto userRequestDto) {
        User user = this.userRepository
                .findByUsername(userRequestDto.getUsername())
                .filter(u -> this.passwordEncoder.matches(userRequestDto.getPassword(), u.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials."));

        return JwtUtil.generateToken(user.getUsername());
    }
}
