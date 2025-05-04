package br.com.luiabdiel.smart_health_system.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserRequestDto {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
