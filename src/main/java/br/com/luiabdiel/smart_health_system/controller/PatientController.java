package br.com.luiabdiel.smart_health_system.controller;

import br.com.luiabdiel.smart_health_system.controller.dto.PatientRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.PatientResponseDto;
import br.com.luiabdiel.smart_health_system.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponseDto> create(@RequestBody @Valid PatientRequestDto patientRequestDto) {
        PatientResponseDto patientResponseDto = this.patientService.create(patientRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponseDto>> findAll(Pageable pageable) {
        Page<PatientResponseDto> patientsResponseDto = this.patientService.findAll(pageable);

        return ResponseEntity.ok().body(patientsResponseDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PatientResponseDto> findById(@PathVariable(value = "id") Long id) {
        PatientResponseDto patientResponseDto = this.patientService.findById(id);

        return ResponseEntity.ok().body(patientResponseDto);
    }
}
