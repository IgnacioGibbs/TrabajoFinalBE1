package com.dh.clinica;

import com.dh.clinica.dto.OdontologoDTO;
import com.dh.clinica.dto.PacienteDTO;
import com.dh.clinica.dto.TurnoDTO;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.TurnoRepository;
import com.dh.clinica.service.TurnoService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class TurnoServiceTest {
    @Mock
    private TurnoRepository turnoRepository;

    @InjectMocks
    private TurnoService turnoService;
    @Spy
    private ObjectMapper mapper = new ObjectMapper();

    TurnoServiceTest() throws JsonProcessingException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testCreateTurno() {
        // Configurar mapper para convertir LocalDate a String
        mapper.registerModule(new JavaTimeModule());

        // Configurar comportamiento del mock
        TurnoDTO turnoDTO = createTurnoDTO();

        Turno turnoMock = mapper.convertValue(turnoDTO, Turno.class);
        when(turnoRepository.save(any(Turno.class))).thenReturn(turnoMock);

        // Ejecutar el método a probar
        TurnoDTO result = turnoService.createTurno(turnoDTO);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(turnoDTO.getId(), result.getId());
        assertNotNull(result.getFechaTurno());
        assertNotNull(result.getOdontologo());
        assertNotNull(result.getPaciente());

        // Verificar que el método save del repositorio se haya llamado
        verify(turnoRepository).save(any(Turno.class));
    }

    private TurnoDTO createTurnoDTO() {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(1L);
        turnoDTO.setFechaTurno(new Date());
        turnoDTO.setOdontologo(new OdontologoDTO());
        turnoDTO.setPaciente(new PacienteDTO());
        return turnoDTO;
    }

    @Test
    public void testGetTurno() throws ResourceNotFoundException {
        // Configurar mapper para convertir LocalDate a String
        mapper.registerModule(new JavaTimeModule());

        // Configurar comportamiento del mock
        Turno turno = new Turno();
        turno.setId(1L);
        turno.setFechaTurno(LocalDateTime.now());
        turno.setOdontologo(new Odontologo());
        turno.setPaciente(new Paciente());
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));

        // Ejecutar el método a probar
        TurnoDTO result = turnoService.getTurno(1L);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNotNull(result.getFechaTurno());
        assertNotNull(result.getOdontologo());
        assertNotNull(result.getPaciente());

        // Verificar que el método findById del repositorio se haya llamado
        verify(turnoRepository).findById(1L);
    }

    @Test
    public void testGetAllTurno() {
        // Configurar mapper para convertir LocalDate a String
        mapper.registerModule(new JavaTimeModule());
        // Configurar comportamiento del mock
        Turno turno1 = new Turno();
        turno1.setId(1L);
        turno1.setFechaTurno(LocalDateTime.now());
        turno1.setOdontologo(new Odontologo());
        turno1.setPaciente(new Paciente());
        Turno turno2 = new Turno();
        turno2.setId(2L);
        turno2.setFechaTurno(LocalDateTime.now());
        turno2.setOdontologo(new Odontologo());
        turno2.setPaciente(new Paciente());
        List<Turno> turnos = List.of(turno1, turno2);
        when(turnoRepository.findAll()).thenReturn(turnos);

        // Ejecutar el método a probar
        List<TurnoDTO> result = turnoService.getAllTurno();

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verificar que el método findAll del repositorio se haya llamado
        verify(turnoRepository).findAll();
    }

    @Test
    public void testUpdateTurno() throws JsonMappingException {
        // Configurar mapper para convertir LocalDate a String
        mapper.registerModule(new JavaTimeModule());

        // Configurar comportamiento del mock
        Turno turno = new Turno();
        turno.setId(1L);
        turno.setFechaTurno(LocalDateTime.now());
        turno.setOdontologo(new Odontologo());
        turno.setPaciente(new Paciente());
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);

        // Ejecutar el método a probar
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(1L);
        turnoDTO.setFechaTurno(new Date());
        turnoDTO.setOdontologo(new OdontologoDTO());
        turnoDTO.setPaciente(new PacienteDTO());
        TurnoDTO result = turnoService.updateTurno(1L, turnoDTO);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNotNull(result.getFechaTurno());
        assertNotNull(result.getOdontologo());
        assertNotNull(result.getPaciente());

        // Verificar que el método findById y save del repositorio se hayan llamado
        verify(turnoRepository).findById(1L);
        verify(turnoRepository).save(any(Turno.class));
    }

    @Test
    public void testDeleteTurno() {
        // Configurar comportamiento del mock
        Turno turno = new Turno();
        turno.setId(1L);
        turno.setFechaTurno(LocalDateTime.now());
        turno.setOdontologo(new Odontologo());
        turno.setPaciente(new Paciente());
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));

        // Ejecutar el método a probar
        assertDoesNotThrow(() -> turnoService.deleteTurno(1L));

        // Verificar que el método findById y delete del repositorio se hayan llamado
        verify(turnoRepository).findById(1L);
        verify(turnoRepository).delete(turno);
    }
}
