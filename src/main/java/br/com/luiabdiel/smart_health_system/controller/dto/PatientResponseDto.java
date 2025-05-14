package br.com.luiabdiel.smart_health_system.controller.dto;

import br.com.luiabdiel.smart_health_system.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PatientResponseDto {

    private Long id;

    private String name;

    private String cpf;

    public Patient toEntity() {
        return new Patient(this.id, this.name, this.cpf, null, null, null);
    }

    public static PatientResponseDto fromEntity(Patient patient) {
        return new PatientResponseDto(patient.getId(), patient.getName(), patient.getCpf());
    }
}
