package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.PatientRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.PatientResponseDto;
import br.com.luiabdiel.smart_health_system.model.Patient;
import br.com.luiabdiel.smart_health_system.repository.PatientRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientResponseDto create(PatientRequestDto patientRequestDto) {
        this.patientRepository
                .findByCpf(patientRequestDto.getCpf())
                .ifPresent(patient -> { throw new IllegalArgumentException("CPF already registered");});
        Patient patient = this.patientRepository.save(patientRequestDto.toEntity());

        return PatientResponseDto.fromEntity(patient);
    }

    public Page<PatientResponseDto> findAll(Pageable pageable) {
        Page<Patient> patients = this.patientRepository.findAll(pageable);

        return patients.map(PatientResponseDto::fromEntity);
    }
}
