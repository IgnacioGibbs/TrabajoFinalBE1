package com.dh.clinica.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomicilioDTO {
    private Long id;
    private String calle;
    private Integer numero;
    private String localidad;
    private String provincia;
}
