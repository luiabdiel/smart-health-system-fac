package br.com.luiabdiel.smart_health_system.controller.dto;

import br.com.luiabdiel.smart_health_system.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DoctorResponseDto {

    private Long id;

    private String name;

    private String crm;

    private String specialty;

    public Doctor toEntity() {
        return new Doctor(this.id, this.name, this.crm, this.specialty);
    }

    public static DoctorResponseDto fromEntity(Doctor doctor) {
        return new DoctorResponseDto(doctor.getId(), doctor.getName(), doctor.getCrm(), doctor.getSpecialty());
    }
}
