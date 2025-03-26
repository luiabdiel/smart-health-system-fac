package br.com.luiabdiel.smart_health_system.controller;

import br.com.luiabdiel.smart_health_system.controller.dto.DoctorRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.DoctorResponseDto;
import br.com.luiabdiel.smart_health_system.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<DoctorResponseDto>> findAll(Pageable pageable) {
        Page<DoctorResponseDto> doctorsResponseDto = this.doctorService.findAll(pageable);

        return ResponseEntity.ok().body(doctorsResponseDto);
    }

    @GetMapping(value = "/{crm}")
    public ResponseEntity<DoctorResponseDto> findByCrm(@PathVariable(value = "crm") String crm) {
        DoctorResponseDto doctorResponseDto = this.doctorService.findByCrm(crm);

        return ResponseEntity.ok().body(doctorResponseDto);
    }
}
