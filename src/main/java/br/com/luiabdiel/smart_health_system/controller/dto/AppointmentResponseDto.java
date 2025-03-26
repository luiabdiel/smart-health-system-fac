package br.com.luiabdiel.smart_health_system.controller.dto;

import br.com.luiabdiel.smart_health_system.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AppointmentResponseDto {

    private Long id;

    private LocalDate date;

    private PatientResponseDto patient;

    private DoctorResponseDto doctor;

    public static AppointmentResponseDto fromEntity(Appointment appointment) {
        return new AppointmentResponseDto(
                appointment.getId(),
                appointment.getDate(),
                PatientResponseDto.fromEntity(appointment.getPatient()),
                DoctorResponseDto.fromEntity(appointment.getDoctor())
        );
    }
}
