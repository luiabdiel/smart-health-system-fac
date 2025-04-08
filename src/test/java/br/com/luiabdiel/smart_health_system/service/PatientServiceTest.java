package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.PatientRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.PatientResponseDto;
import br.com.luiabdiel.smart_health_system.model.Patient;
import br.com.luiabdiel.smart_health_system.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
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

}