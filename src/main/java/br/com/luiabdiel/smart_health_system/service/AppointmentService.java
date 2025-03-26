package br.com.luiabdiel.smart_health_system.service;

import br.com.luiabdiel.smart_health_system.controller.dto.AppointmentRequestDto;
import br.com.luiabdiel.smart_health_system.controller.dto.AppointmentResponseDto;
import br.com.luiabdiel.smart_health_system.model.Appointment;
import br.com.luiabdiel.smart_health_system.model.Doctor;
import br.com.luiabdiel.smart_health_system.model.Patient;
import br.com.luiabdiel.smart_health_system.repository.AppointmentRepository;
import br.com.luiabdiel.smart_health_system.repository.DoctorRepository;
import br.com.luiabdiel.smart_health_system.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentResponseDto create(AppointmentRequestDto appointmentRequestDto) {
        Patient patient = this.patientRepository
                .findById(appointmentRequestDto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        Doctor doctor = this.doctorRepository
                .findById(appointmentRequestDto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        Appointment appointment = new Appointment();
        appointment.setDate(appointmentRequestDto.getDate());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment savedAppointment = this.appointmentRepository.save(appointment);

        return AppointmentResponseDto.fromEntity(savedAppointment);
    }

    public Page<AppointmentResponseDto> findAll(Pageable pageable) {
        Page<Appointment> appointments = this.appointmentRepository.findAll(pageable);

        return appointments.map(AppointmentResponseDto::fromEntity);
    }
}
