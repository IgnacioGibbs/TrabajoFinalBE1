package com.dh.clinica.entity;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "DOMICILIOS")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_domicilio")
    private Long id;

    @NotBlank
    @Size(max = 128)
    private String calle;

    @NotBlank
    @Positive
    @Size(max = 5)
    private Integer numero;

    @NotBlank
    @Size(max = 128)
    private String localidad;

    @NotBlank
    @Size(max = 128)
    private String provincia;

}
