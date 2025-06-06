package br.com.luiabdiel.smart_health_system.controller.dto;

import br.com.luiabdiel.smart_health_system.model.Patient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
public class PatientRequestDto {

    @NotNull
    private String name;

    @Pattern(regexp = "\\d{11}", message = "CPF inválido")  // CPF com 14 dígitos
    @NotNull
    private String cpf;

    @NotNull
    private LocalDate birthDate;

    @Pattern(regexp = "\\(\\d{2}\\) \\d{4}-\\d{4}", message = "Telefone inválido") // (XX) XXXX-XXXX
    @NotNull
    private String phone;

    public Patient toEntity() {
        return new Patient(null, this.name, this.cpf, this.birthDate, this.phone, null);
    }
}
