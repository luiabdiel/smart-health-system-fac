package br.com.luiabdiel.smart_health_system.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AppointmentRequestDto {

    private LocalDate date;

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;
}
