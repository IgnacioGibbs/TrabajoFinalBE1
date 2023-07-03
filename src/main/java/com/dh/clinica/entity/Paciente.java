package com.dh.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PACIENTES")
@NoArgsConstructor
@ToString
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long id;

    @NotBlank
    @Size(max = 32)
    private String nombre;

    @NotBlank
    @Size(max = 32)
    private String apellido;

    @NotBlank
    @Column(unique = true)
    @Positive
    @Size(max = 8)
    private Integer dni;

    @NotBlank
    private Date fechaIngreso;

    @OneToOne
    @JoinColumn(name = "id_domicilio", referencedColumnName = "id_domicilio")
    private Domicilio domicilio;
}
