package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.DoctorRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.DoctorResponseDto;
import br.com.luiabdiel.smart_health_system.model.Doctor;
import br.com.luiabdiel.smart_health_system.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Test
    void shouldCreateDoctorSuccessfully() {
        var expectedId = 1L;
        var expectedName = "any";
        var expectedCrm = "any";
        var expectedSpecialty = "any";

        Doctor doctor = new Doctor(expectedId, expectedName, expectedCrm, expectedSpecialty);
        DoctorRequestDto doctorRequestDto = new DoctorRequestDto(expectedName, expectedCrm, expectedSpecialty);

        when(this.doctorRepository.save(any())).thenReturn(doctor);
        DoctorResponseDto response = this.doctorService.create(doctorRequestDto);

        assertNotNull(response);
        assertEquals(expectedId, response.getId());
        verify(this.doctorRepository, times(1)).save(any());
    }

    @Test
    void shouldFindAllDoctorsSuccessfully() {
        var expectedId = 1L;
        var expectedName = "any";
        var expectedCrm = "any";
        var expectedSpecialty = "any";

        Doctor doctor = new Doctor(expectedId, expectedName, expectedCrm, expectedSpecialty);
        Page<Doctor> expectedPage = new PageImpl<>(List.of(doctor));
        Pageable pageable = PageRequest.of(0, 10);

        when(this.doctorRepository.findAll(pageable)).thenReturn(expectedPage);
        Page<DoctorResponseDto> response = this.doctorService.findAll(pageable);

        assertNotNull(response);
        assertEquals(expectedId, response.getContent().get(0).getId());
        verify(this.doctorRepository, times(1)).findAll(pageable);
    }
}