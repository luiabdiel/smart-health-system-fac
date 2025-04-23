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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentResponseDto create(AppointmentRequestDto appointmentRequestDto) {
        log.info("Iniciando criação de agendamento. Dados recebidos: {}", appointmentRequestDto);

        Patient patient = this.patientRepository
                .findById(appointmentRequestDto.getPatientId())
                .orElseThrow(() -> {
                    log.error("Paciente com ID {} não encontrado.", appointmentRequestDto.getPatientId());
                    return new IllegalArgumentException("Patient not found");
                });

        Doctor doctor = this.doctorRepository
                .findById(appointmentRequestDto.getDoctorId())
                .orElseThrow(() -> {
                    log.error("Médico com ID {} não encontrado.", appointmentRequestDto.getDoctorId());
                    return new IllegalArgumentException("Doctor not found");
                });

        Appointment appointment = new Appointment();
        appointment.setDate(appointmentRequestDto.getDate());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        Appointment savedAppointment = this.appointmentRepository.save(appointment);

        log.info("Agendamento criado com sucesso. ID do agendamento: {}", savedAppointment.getId());

        return AppointmentResponseDto.fromEntity(savedAppointment);
    }


    public Page<AppointmentResponseDto> findAll(Pageable pageable) {
        log.info("Iniciando busca paginada de agendamentos. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());
        Page<Appointment> appointments = this.appointmentRepository.findAll(pageable);

        log.info("Busca finalizada. Total de agendamentos encontrados: {}", appointments.getTotalElements());
        return appointments.map(AppointmentResponseDto::fromEntity);
    }

    public Page<AppointmentResponseDto> findAllAppointmentsByDoctorId(Pageable pageable, Long id) {
        log.info("Iniciando busca paginada de agendamentos para o médico com ID: {}. Página: {}, Tamanho: {}",
                id, pageable.getPageNumber(), pageable.getPageSize());
        Page<Appointment> appointments = this.appointmentRepository.findAllAppointmentsByDoctorId(pageable, id);

        log.info("Busca finalizada. Total de agendamentos encontrados para o médico {}: {}",
                id, appointments.getTotalElements());
        return appointments.map(AppointmentResponseDto::fromEntity);
    }

    public Page<AppointmentResponseDto> findAllAppointmentsByPatientId(Pageable pageable, Long id) {
        log.info("Iniciando busca paginada de agendamentos para o paciente com ID: {}. Página: {}, Tamanho: {}",
                id, pageable.getPageNumber(), pageable.getPageSize());
        Page<Appointment> appointments = this.appointmentRepository.findAllAppointmentsByPatientId(pageable, id);

        log.info("Busca finalizada. Total de agendamentos encontrados para o paciente {}: {}",
                id, appointments.getTotalElements());
        return appointments.map(AppointmentResponseDto::fromEntity);
    }
}
