package com.dh.clinica.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "TURNOS")
@NoArgsConstructor
@ToString
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno")
    private Long id;

    @NotBlank
    private Date fechaTurno;

    @ManyToOne
    private Odontologo odontologo;

    @ManyToOne
    private Paciente paciente;
}
