package br.com.luiabdiel.smart_health_system.controller;

import br.com.luiabdiel.smart_health_system.controller.dto.AppointmentRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.AppointmentResponseDto;
import br.com.luiabdiel.smart_health_system.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> create(@RequestBody @Valid AppointmentRequestDto appointmentRequestDto) {
        AppointmentResponseDto appointmentResponseDto = this.appointmentService.create(appointmentRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentResponseDto>> findAll(Pageable pageable) {
        Page<AppointmentResponseDto> appointmentsResponseDto = this.appointmentService.findAll(pageable);

        return ResponseEntity.ok().body(appointmentsResponseDto);
    }

    @GetMapping(value = "/doctor/{id}")
    public ResponseEntity<Page<AppointmentResponseDto>> findAllAppointmentsByDoctorId(
            Pageable pageable,
            @PathVariable(value = "id") Long id
    ) {
        Page<AppointmentResponseDto> appointmentsResponseDto = this.appointmentService.
                findAllAppointmentsByDoctorId(pageable, id);

        return ResponseEntity.ok().body(appointmentsResponseDto);
    }

    @GetMapping(value = "/patient/{id}")
    public ResponseEntity<Page<AppointmentResponseDto>> findAllAppointmentsByPatientId(
            Pageable pageable,
            @PathVariable(value = "id") Long id
    ) {
        Page<AppointmentResponseDto> appointmentsResponseDto = this.appointmentService.
                findAllAppointmentsByPatientId(pageable, id);

        return ResponseEntity.ok().body(appointmentsResponseDto);
    }
}
