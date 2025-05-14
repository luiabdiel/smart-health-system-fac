package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.UserRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.UserResponseDto;
import br.com.luiabdiel.smart_health_system.model.User;
import br.com.luiabdiel.smart_health_system.repository.UserRepository;
import br.com.luiabdiel.smart_health_system.shared.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        log.info("Iniciando registro de novo usuário: {}", userRequestDto.getUsername());
        String encryptedPassword = this.passwordEncoder.encode(userRequestDto.getPassword());
        User user = new User(null, userRequestDto.getUsername(), encryptedPassword);

        var savedUser = this.userRepository.save(user);

        log.info("Usuário registrado com sucesso. ID: {}", savedUser.getId());
        return UserResponseDto.fromEntity(savedUser);
    }

    public UserResponseDto findByUsername(String username) {
        log.info("Iniciando busca de usuário com username: {}", username);
        User user = this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Usuário com username '{}' não encontrado.", username);
                    return new IllegalArgumentException("User not found.");
                });

        log.info("Usuário encontrado. ID: {}", user.getId());
        return UserResponseDto.fromEntity(user);
    }

    public String login(UserRequestDto userRequestDto) {
        log.info("Tentando autenticar usuário: {}", userRequestDto.getUsername());

        User user = this.userRepository
                .findByUsername(userRequestDto.getUsername())
                .filter(u -> this.passwordEncoder.matches(userRequestDto.getPassword(), u.getPassword()))
                .orElseThrow(() -> {
                    log.warn("Falha na autenticação para o usuário: {}", userRequestDto.getUsername());
                    return new IllegalArgumentException("Invalid credentials.");
                });

        String token = JwtUtil.generateToken(user.getUsername());
        log.info("Usuário autenticado com sucesso: {}. Token gerado.", user.getUsername());

        return token;
    }
}
