package com.dh.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ODONTOLOGOS")
@NoArgsConstructor
@ToString
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_odontologo")
    private Long id;

    @NotBlank
    @Size(max = 128)
    private String nombre;

    @NotBlank
    @Size(max = 128)
    private String apellido;

    @NotBlank
    @Column(unique = true)
    @Positive
    @Size(max = 12)
    private Integer matricula;

    @Override
    public String toString() {
        return "Odontologo {" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", matricula=" + matricula +
                '}';
    }
}
