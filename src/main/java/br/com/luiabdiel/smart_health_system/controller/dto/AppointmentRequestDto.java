package br.com.luiabdiel.smart_health_system.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class AppointmentRequestDto {

    private LocalDate date;

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;
}
