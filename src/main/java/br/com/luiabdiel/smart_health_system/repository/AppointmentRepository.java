package br.com.luiabdiel.smart_health_system.repository;

import br.com.luiabdiel.smart_health_system.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findAllAppointmentsByDoctorId(Pageable pageable, Long id);
}
