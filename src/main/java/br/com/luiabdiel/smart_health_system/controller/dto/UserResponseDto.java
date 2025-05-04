package br.com.luiabdiel.smart_health_system.controller.dto;

import br.com.luiabdiel.smart_health_system.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserResponseDto {

    private Long id;

    private String username;

    public User toEntity() {
        return new User(this.id, this.username, null);
    }

    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(user.getId(), user.getUsername());
    }
}
