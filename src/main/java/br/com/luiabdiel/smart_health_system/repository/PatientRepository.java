package br.com.luiabdiel.smart_health_system.repository;

import br.com.luiabdiel.smart_health_system.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByCpf(String cpf);
}
