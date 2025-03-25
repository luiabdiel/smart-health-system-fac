package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.DoctorRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.DoctorResponseDto;
import br.com.luiabdiel.smart_health_system.model.Doctor;
import br.com.luiabdiel.smart_health_system.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorResponseDto create(DoctorRequestDto doctorRequestDto) {
        this.doctorRepository
                .findByCrm(doctorRequestDto.getCrm())
                .ifPresent(doctor -> { throw new IllegalArgumentException("CRM already registered");});

        Doctor doctor = this.doctorRepository.save(doctorRequestDto.toEntity());

        return DoctorResponseDto.fromEntity(doctor);
    }

    public Page<DoctorResponseDto> findAll(Pageable pageable) {
        Page<Doctor> patients = this.doctorRepository.findAll(pageable);

        return patients.map(DoctorResponseDto::fromEntity);
    }

    public DoctorResponseDto findByCrm(String crm) {
        Doctor doctor = this.doctorRepository
                .findByCrm(crm)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        return DoctorResponseDto.fromEntity(doctor);
    }
}
