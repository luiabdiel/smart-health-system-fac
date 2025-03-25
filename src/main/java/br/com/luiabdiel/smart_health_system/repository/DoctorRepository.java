package br.com.luiabdiel.smart_health_system.repository;

import br.com.luiabdiel.smart_health_system.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByCrm(String crm);
}
