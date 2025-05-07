package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.DoctorRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.DoctorResponseDto;
import br.com.luiabdiel.smart_health_system.model.Doctor;
import br.com.luiabdiel.smart_health_system.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorResponseDto create(DoctorRequestDto doctorRequestDto) {
        log.info("Iniciando o cadastro do médico com CRM: {}", doctorRequestDto.getCrm());

        this.doctorRepository
                .findByCrm(doctorRequestDto.getCrm())
                .ifPresent(doctor -> {
                    log.warn("CRM já registrado: {}", doctorRequestDto.getCrm());
                    throw new IllegalArgumentException("CRM already registered");
                });

        Doctor doctor = this.doctorRepository.save(doctorRequestDto.toEntity());

        log.info("Médico cadastrado com sucesso. ID: {}", doctor.getId());

        return DoctorResponseDto.fromEntity(doctor);
    }

    public Page<DoctorResponseDto> findAll(Pageable pageable) {
        log.info("Iniciando busca paginada de médicos. Página: {}, Tamanho: {}", pageable.getPageNumber(), pageable.getPageSize());

        Page<Doctor> doctors = this.doctorRepository.findAll(pageable);

        log.info("Busca finalizada. Total de médicos encontrados: {}", doctors.getTotalElements());

        return doctors.map(DoctorResponseDto::fromEntity);
    }

    public DoctorResponseDto findByCrm(String crm) {
        log.info("Iniciando busca pelo médico com CRM: {}", crm);

        Doctor doctor = this.doctorRepository
                .findByCrm(crm)
                .orElseThrow(() -> {
                    log.warn("Médico com CRM '{}' não encontrado.", crm);
                    return new IllegalArgumentException("Doctor not found");
                });

        log.info("Médico encontrado com CRM: {}", crm);

        return DoctorResponseDto.fromEntity(doctor);
    }
}
