package br.com.luiabdiel.smart_health_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "TB_PATIENTS")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private LocalDate birthDate;

    private String phone;
}
