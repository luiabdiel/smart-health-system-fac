package br.com.luiabdiel.smart_health_system.controller;

import br.com.luiabdiel.smart_health_system.controller.dto.UserRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.UserResponseDto;
import br.com.luiabdiel.smart_health_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = this.userService.registerUser(userRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto userRequestDto) {
        String token = this.userService.login(userRequestDto);

        return ResponseEntity.ok(token);
    }
}
