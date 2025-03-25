package br.com.luiabdiel.smart_health_system.controller;

import br.com.luiabdiel.smart_health_system.controller.dto.DoctorRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.DoctorResponseDto;
import br.com.luiabdiel.smart_health_system.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorResponseDto> create(@RequestBody @Valid DoctorRequestDto doctorRequestDto) {
        DoctorResponseDto doctorResponseDto = this.doctorService.create(doctorRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(doctorResponseDto);
    }
}
