package com.dh.clinica.dto;

import com.dh.clinica.entity.Turno;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OdontologoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private Integer matricula;
}
