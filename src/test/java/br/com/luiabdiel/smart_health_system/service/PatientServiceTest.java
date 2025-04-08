package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.PatientRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.PatientResponseDto;
import br.com.luiabdiel.smart_health_system.model.Patient;
import br.com.luiabdiel.smart_health_system.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Test
    void shouldCreatePatientSuccessfully() {
        var expectedId = 1L;
        var expectedName = "any";
        var expectedCpf = "any";
        var expectedBirthDate = LocalDate.of(2025,4, 8);
        var expectedPhone = "any";

        Patient patient = new Patient(
                expectedId,
                expectedName,
                expectedCpf,
                expectedBirthDate,
                expectedPhone
        );
        PatientRequestDto patientRequestDto = new PatientRequestDto(
                expectedName,
                expectedCpf,
                expectedBirthDate,
                expectedPhone
        );

        when(this.patientRepository.save(any())).thenReturn(patient);
        PatientResponseDto response = this.patientService.create(patientRequestDto);

        assertNotNull(response);
        assertEquals(expectedId, response.getId());
        verify(this.patientRepository, times(1)).save(any());
    }

    @Test
    void shouldFindAllPatientSuccessfully() {
        var expectedId = 1L;
        var expectedName = "any";
        var expectedCpf = "any";
        var expectedBirthDate = LocalDate.of(2025,4, 8);
        var expectedPhone = "any";

        Patient patient = new Patient(
                expectedId,
                expectedName,
                expectedCpf,
                expectedBirthDate,
                expectedPhone
        );
        Page<Patient> expectedPage = new PageImpl<>(List.of(patient));
        Pageable pageable = PageRequest.of(0, 10);

        when(this.patientRepository.findAll(pageable)).thenReturn(expectedPage);
        Page<PatientResponseDto> response = this.patientService.findAll(pageable);

        assertNotNull(response);
        assertEquals(expectedId, response.getContent().get(0).getId());
        verify(this.patientRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldFindPatientByIdSuccessfully() {
        var expectedId = 1L;
        var expectedName = "any";
        var expectedCpf = "any";
        var expectedBirthDate = LocalDate.of(2025,4, 8);
        var expectedPhone = "any";

        Patient patient = new Patient(
                expectedId,
                expectedName,
                expectedCpf,
                expectedBirthDate,
                expectedPhone
        );

        when(this.patientRepository.findById(any())).thenReturn(Optional.of(patient));
        PatientResponseDto response = this.patientService.findById(expectedId);

        assertNotNull(response);
        assertEquals(expectedId, response.getId());
        verify(this.patientRepository, times(1)).findById(any());
    }
}