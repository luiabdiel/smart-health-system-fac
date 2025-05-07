package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.PatientRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.PatientResponseDto;
import br.com.luiabdiel.smart_health_system.model.Patient;
import br.com.luiabdiel.smart_health_system.repository.PatientRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientResponseDto create(PatientRequestDto patientRequestDto) {
        log.info("Iniciando o cadastro do paciente com CPF: {}", patientRequestDto.getCpf());
        this.patientRepository
                .findByCpf(patientRequestDto.getCpf())
                .ifPresent(patient -> {
                    log.warn("CPF já registrado: {}", patientRequestDto.getCpf());
                    throw new IllegalArgumentException("CPF already registered");
                });

        Patient patient = this.patientRepository.save(patientRequestDto.toEntity());

        log.info("Paciente cadastrado com sucesso. ID: {}", patient.getId());
        return PatientResponseDto.fromEntity(patient);
    }

    public Page<PatientResponseDto> findAll(Pageable pageable) {
        log.info("Iniciando busca paginada de pacientes. Página: {}, Tamanho: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Patient> patients = this.patientRepository.findAll(pageable);

        log.info("Busca finalizada. Total de pacientes encontrados: {}", patients.getTotalElements());
        return patients.map(PatientResponseDto::fromEntity);
    }

    public PatientResponseDto findById(Long id) {
        log.info("Iniciando busca pelo paciente com ID: {}", id);
        Patient patient = this.patientRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Paciente com ID '{}' não encontrado.", id);
                    return new IllegalArgumentException("Patient not found");
                });

        log.info("Paciente encontrado. ID: {}", id);
        return PatientResponseDto.fromEntity(patient);
    }
}
