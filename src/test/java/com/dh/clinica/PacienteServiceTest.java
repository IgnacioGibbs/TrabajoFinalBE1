package com.dh.clinica;

import com.dh.clinica.dto.DomicilioDTO;
import com.dh.clinica.dto.PacienteDTO;
import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.PacienteRepository;
import com.dh.clinica.service.PacienteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class PacienteServiceTest {
    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @Spy
    private ObjectMapper mapper = new ObjectMapper();

    PacienteServiceTest() throws JsonProcessingException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePaciente() {
        // Configurar mapper para convertir LocalDate a String
        mapper.registerModule(new JavaTimeModule());

        // Configurar comportamiento del mock
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(1L);
        pacienteDTO.setNombre("John");
        pacienteDTO.setApellido("Doe");
        pacienteDTO.setDni(123456789);
        pacienteDTO.setFechaIngreso(new Date());
        pacienteDTO.setDomicilio(new DomicilioDTO());
        pacienteDTO.setTurnos(new ArrayList<>());

        Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        // Ejecutar el método a probar
        PacienteDTO result = pacienteService.createPaciente(pacienteDTO);

        // Verificar el resultado
        assertEquals(1L, result.getId());
        assertEquals("John", result.getNombre());
        assertEquals("Doe", result.getApellido());
        assertEquals(123456789, result.getDni());
        assertEquals(LocalDate.now(), result.getFechaIngreso());
        assertNotNull(result.getDomicilio());
        assertTrue(Objects.isNull(result.getTurnos()));

        // Verificar que el método save del repositorio se haya llamado
        verify(pacienteRepository).save(any(Paciente.class));
    }

    @Test
    void testGetPaciente() throws ResourceNotFoundException {

        // Configurar mapper para convertir LocalDate a String
        mapper.registerModule(new JavaTimeModule());

        // Configurar comportamiento del mock
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("John");
        paciente.setApellido("Doe");
        paciente.setDni(123456789);
        paciente.setFechaIngreso(new Date());
        paciente.setDomicilio(new Domicilio());
        paciente.setTurnos(new ArrayList<>());
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        // Ejecutar el método a probar
        PacienteDTO result = pacienteService.getPaciente(1L);

        // Verificar el resultado
        assertEquals(1L, result.getId());
        assertEquals("John", result.getNombre());
        assertEquals("Doe", result.getApellido());
        assertEquals(123456789, result.getDni());
        assertEquals(LocalDate.now(), result.getFechaIngreso());
        assertNotNull(result.getDomicilio());
        assertTrue(Objects.isNull(result.getTurnos()));

        // Verificar que el método findById del repositorio se haya llamado
        verify(pacienteRepository).findById(1L);
    }

    @Test
    void testGetAllPacientes() {

        // Configurar mapper para convertir LocalDate a String
        mapper.registerModule(new JavaTimeModule());

        // Configurar comportamiento del mock
        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);
        paciente1.setNombre("John");
        paciente1.setApellido("Doe");
        paciente1.setDni(123456789);
        paciente1.setFechaIngreso(new Date());
        paciente1.setDomicilio(new Domicilio());
        paciente1.setTurnos(new ArrayList<>());
        Paciente paciente2 = new Paciente();
        paciente2.setId(2L);
        paciente2.setNombre("Jane");
        paciente2.setApellido("Smith");
        paciente2.setDni(987654321);
        paciente2.setFechaIngreso(new Date());
        paciente2.setDomicilio(new Domicilio());
        paciente2.setTurnos(new ArrayList<>());
        List<Paciente> pacientes = List.of(paciente1, paciente2);
        when(pacienteRepository.findAll()).thenReturn(pacientes);

        // Ejecutar el método a probar
        List<PacienteDTO> result = pacienteService.getAllPacientes();

        // Verificar el resultado
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("John", result.get(0).getNombre());
        assertEquals("Doe", result.get(0).getApellido());
        assertEquals(123456789, result.get(0).getDni());
        assertEquals(LocalDate.now(), result.get(0).getFechaIngreso());
        assertTrue(Objects.isNull(result.get(0).getTurnos()));
        assertEquals(2L, result.get(1).getId());
        assertEquals("Jane", result.get(1).getNombre());
        assertEquals("Smith", result.get(1).getApellido());
        assertEquals(987654321, result.get(1).getDni());
        assertEquals(LocalDate.now(), result.get(1).getFechaIngreso());
        assertNotNull(result.get(1).getDomicilio());
        assertTrue(Objects.isNull(result.get(0).getTurnos()));

        // Verificar que el método findAll del repositorio se haya llamado
        verify(pacienteRepository).findAll();
    }

    @Test
    void testUpdatePaciente() throws JsonMappingException, ResourceNotFoundException {
        // Configurar mapper para convertir LocalDate a String
        mapper.registerModule(new JavaTimeModule());

        // Configurar comportamiento del mock
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("John");
        paciente.setApellido("Doe");
        paciente.setDni(123456789);
        paciente.setFechaIngreso(new Date());
        paciente.setDomicilio(new Domicilio());
        paciente.setTurnos(new ArrayList<>());
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        // Configurar el DTO de actualización
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(1L);
        pacienteDTO.setNombre("Jane");
        pacienteDTO.setApellido("Smith");
        pacienteDTO.setDni(987654321);
        pacienteDTO.setFechaIngreso(new Date());
        pacienteDTO.setDomicilio(new DomicilioDTO());
        pacienteDTO.setTurnos(new ArrayList<>());

        // Ejecutar el método a probar
        PacienteDTO result = pacienteService.updatePaciente(1L, pacienteDTO);

        // Verificar el resultado
        assertEquals(1L, result.getId());
        assertEquals("Jane", result.getNombre());
        assertEquals("Smith", result.getApellido());
        assertEquals(987654321, result.getDni());
        assertEquals(LocalDate.now(), result.getFechaIngreso());
        assertNotNull(result.getDomicilio());
        assertTrue(Objects.isNull(result.getTurnos()));

        // Verificar que el método findById y save del repositorio se hayan llamado
        verify(pacienteRepository).findById(1L);
        verify(pacienteRepository).save(any(Paciente.class));
    }

    @Test
    void testDeletePaciente() {
        // Configurar comportamiento del mock
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("John");
        paciente.setApellido("Doe");
        paciente.setDni(123456789);
        paciente.setFechaIngreso(new Date());
        paciente.setDomicilio(new Domicilio());
        paciente.setTurnos(new ArrayList<>());
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        // Ejecutar el método a probar
        assertDoesNotThrow(() -> pacienteService.deletePaciente(1L));

        // Verificar que el método findById y delete del repositorio se hayan llamado
        verify(pacienteRepository).findById(1L);
        verify(pacienteRepository).delete(paciente);
    }
}
