package br.com.luiabdiel.smart_health_system.controller.dto;

import br.com.luiabdiel.smart_health_system.model.Doctor;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class DoctorRequestDto {

    @NotNull
    private String name;

    @NotNull
    private String crm;

    @NotNull
    private String specialty;

    public Doctor toEntity() {
        return new Doctor(null, this.name, this.crm, this.specialty);
    }
}
