package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.model.User;
import br.com.luiabdiel.smart_health_system.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthUserDetailsServiceTest {

    @InjectMocks
    private AuthUserDetailsService authUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldLoadUserByUsernameSuccessfully() {
        String username = "any_user";
        String password = "any_password";
        Long id = 1L;

        User user = new User(id, username, password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = authUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        String username = "nonexistent";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authUserDetailsService.loadUserByUsername(username);
        });

        assertEquals("User not found.", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }
}