package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.UserRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.UserResponseDto;
import br.com.luiabdiel.smart_health_system.model.User;
import br.com.luiabdiel.smart_health_system.repository.UserRepository;
import br.com.luiabdiel.smart_health_system.shared.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldRegisterUserSuccessfully() {
        Long expectedId = 1L;
        String expectedUsername = "any_name";
        String rawPassword = "any_password";
        String encodedPassword = "any_password_encoded";

        UserRequestDto userRequestDto = new UserRequestDto(expectedUsername, rawPassword);
        User savedUser = new User(expectedId, expectedUsername, encodedPassword);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDto response = userService.registerUser(userRequestDto);

        assertNotNull(response);
        assertEquals(expectedId, response.getId());
        assertEquals(expectedUsername, response.getUsername());
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldFindUserByUsernameSuccessfully() {
        Long expectedId = 1L;
        String expectedUsername = "any_name";
        String expectedPassword = "any_password_encoded";

        User user = new User(expectedId, expectedUsername, expectedPassword);

        when(userRepository.findByUsername(expectedUsername)).thenReturn(java.util.Optional.of(user));

        UserResponseDto response = userService.findByUsername(expectedUsername);

        assertNotNull(response);
        assertEquals(expectedId, response.getId());
        assertEquals(expectedUsername, response.getUsername());
        verify(userRepository, times(1)).findByUsername(expectedUsername);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        String username = "nonexistent";

        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.findByUsername(username);
        });

        assertEquals("User not found.", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void shouldLoginSuccessfully() {
        String username = "any_name";
        String rawPassword = "any_password";
        String encodedPassword = "any_password_encoded";
        String expectedToken = "mocked.jwt.token";

        User user = new User(1L, username, encodedPassword);
        UserRequestDto userRequestDto = new UserRequestDto(username, rawPassword);

        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        try (var mockedJwt = mockStatic(JwtUtil.class)) {
            mockedJwt.when(() -> JwtUtil.generateToken(username)).thenReturn(expectedToken);

            String token = userService.login(userRequestDto);

            assertNotNull(token);
            assertEquals(expectedToken, token);
            verify(userRepository, times(1)).findByUsername(username);
            verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
        }
    }

    @Test
    void shouldThrowExceptionWhenInvalidCredentials() {
        String username = "wronguser";
        String rawPassword = "wrongpass";
        String encodedPassword = "encodedPassword123";

        User user = new User(1L, username, encodedPassword);
        UserRequestDto userRequestDto = new UserRequestDto(username, rawPassword);

        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.login(userRequestDto);
        });

        assertEquals("Invalid credentials.", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).matches(rawPassword, encodedPassword);
    }
}