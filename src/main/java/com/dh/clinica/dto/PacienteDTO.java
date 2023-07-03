package com.dh.clinica.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private Integer dni;
    private Date fechaIngreso;
    private DomicilioDTO domicilio;
}
