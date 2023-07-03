package com.dh.clinica.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoDTO {
    private Long id;
    private Date fechaTurno;
    private OdontologoDTO odontologo;
    private PacienteDTO paciente;
}
