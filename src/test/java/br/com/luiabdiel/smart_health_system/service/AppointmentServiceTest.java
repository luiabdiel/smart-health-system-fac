package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.AppointmentRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.AppointmentResponseDto;
import br.com.luiabdiel.smart_health_system.model.Appointment;
import br.com.luiabdiel.smart_health_system.model.Doctor;
import br.com.luiabdiel.smart_health_system.model.Patient;
import br.com.luiabdiel.smart_health_system.repository.AppointmentRepository;
import br.com.luiabdiel.smart_health_system.repository.DoctorRepository;
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
import static org.mockito.Mockito.*;

@SpringBootTest
class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Test
    void shouldCreateAppointmentSuccessfully() {
        var expectedId = 1L;
        var expectedDate = LocalDate.of(2025, 4, 17);
        var expectedPatient = new Patient(
                expectedId,
                "any",
                "any",
                LocalDate.of(2025, 4, 17),
                "any"
        );
        var expectedDoctor = new Doctor(
                expectedId,
                "any",
                "any",
                "any"
        );

        Appointment appointment = new Appointment(
                expectedId,
                expectedDate,
                expectedPatient,
                expectedDoctor
        );
        AppointmentRequestDto appointmentRequestDto = new AppointmentRequestDto(
                expectedDate,
                expectedId,
                expectedId
        );

        when(patientRepository.findById(expectedId)).thenReturn(Optional.of(expectedPatient));
        when(doctorRepository.findById(expectedId)).thenReturn(Optional.of(expectedDoctor));
        when(this.appointmentRepository.save(any())).thenReturn(appointment);

        AppointmentResponseDto response = this.appointmentService.create(appointmentRequestDto);

        assertNotNull(response);
        assertEquals(expectedId, response.getId());
        verify(this.appointmentRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFound() {
        var expectedId = 1L;
        var expectedDate = LocalDate.of(2025, 4, 17);
        AppointmentRequestDto appointmentRequestDto = new AppointmentRequestDto(
                expectedDate,
                expectedId,
                expectedId
        );

        when(patientRepository.findById(expectedId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> appointmentService.create(appointmentRequestDto)
        );

        assertEquals("Patient not found", exception.getMessage());
        verify(patientRepository, times(1)).findById(expectedId);
        verify(doctorRepository, never()).findById(any());
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void shouldFindAllAppointmentsSuccessfully() {
        var expectedId = 1L;
        var expectedDate = LocalDate.of(2025, 4, 17);
        var expectedPatient = new Patient();
        var expectedDoctor = new Doctor();

        Appointment appointment = new Appointment(
                expectedId,
                expectedDate,
                expectedPatient,
                expectedDoctor
        );
        Page<Appointment> expectedPage = new PageImpl<>(List.of(appointment));
        Pageable pageable = PageRequest.of(0, 10);

        when(this.appointmentRepository.findAll(pageable)).thenReturn(expectedPage);
        Page<AppointmentResponseDto> response = this.appointmentService.findAll(pageable);

        assertNotNull(response);
        assertEquals(expectedId, response.getContent().get(0).getId());
        verify(this.appointmentRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldFindAllAppointmentsByDoctorId() {
        var expectedId = 1L;
        var expectedDate = LocalDate.of(2025, 4, 17);
        var expectedPatient = new Patient();
        var expectedDoctor = new Doctor();

        Appointment appointment = new Appointment(
                expectedId,
                expectedDate,
                expectedPatient,
                expectedDoctor
        );
        Page<Appointment> expectedPage = new PageImpl<>(List.of(appointment));
        Pageable pageable = PageRequest.of(0, 10);

        when(this.appointmentRepository.findAllAppointmentsByDoctorId(pageable, expectedId)).thenReturn(expectedPage);
        Page<AppointmentResponseDto> response = this.appointmentService.findAllAppointmentsByDoctorId(pageable, expectedId);

        assertNotNull(response);
        assertEquals(expectedId, response.getContent().get(0).getId());
        verify(this.appointmentRepository, times(1)).findAllAppointmentsByDoctorId(pageable, expectedId);
    }

    @Test
    void shouldFindAllAppointmentsByPatientId() {
        var expectedId = 1L;
        var expectedDate = LocalDate.of(2025, 4, 17);
        var expectedPatient = new Patient();
        var expectedDoctor = new Doctor();

        Appointment appointment = new Appointment(
                expectedId,
                expectedDate,
                expectedPatient,
                expectedDoctor
        );
        Page<Appointment> expectedPage = new PageImpl<>(List.of(appointment));
        Pageable pageable = PageRequest.of(0, 10);

        when(this.appointmentRepository.findAllAppointmentsByPatientId(pageable, expectedId)).thenReturn(expectedPage);
        Page<AppointmentResponseDto> response = this.appointmentService.findAllAppointmentsByPatientId(pageable, expectedId);

        assertNotNull(response);
        assertEquals(expectedId, response.getContent().get(0).getId());
        verify(this.appointmentRepository, times(1)).findAllAppointmentsByPatientId(pageable, expectedId);
    }
}