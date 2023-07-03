package com.dh.clinica;

import com.dh.clinica.dto.OdontologoDTO;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.OdontologoRepository;
import com.dh.clinica.service.OdontologoService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class OdontologoServiceTest {
    @Mock
    private OdontologoRepository odontologoRepository;

    @InjectMocks
    private OdontologoService odontologoService;

    @Spy
    private ObjectMapper mapper = new ObjectMapper();

    OdontologoServiceTest() throws JsonProcessingException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOdontologo() {
        // Crear objeto de prueba
        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setId(1L);
        odontologoDTO.setNombre("John");
        odontologoDTO.setApellido("Doe");
        odontologoDTO.setMatricula(12345);
        odontologoDTO.setTurnos(new ArrayList<>());

        // Configurar comportamiento del mock
        Odontologo odontologo = new Odontologo();
        odontologo.setId(1L);
        odontologo.setNombre("John");
        odontologo.setApellido("Doe");
        odontologo.setMatricula(12345);
        when(odontologoRepository.save(any(Odontologo.class))).thenReturn(odontologo);

        // Ejecutar el método a probar
        OdontologoDTO result = odontologoService.createOdontologo(odontologoDTO);

        // Verificar el resultado
        assertEquals(1L, result.getId());
        assertEquals("John", result.getNombre());
        assertEquals("Doe", result.getApellido());
        assertEquals(12345, result.getMatricula());
        assertTrue(Objects.isNull(result.getTurnos()));

        // Verificar que el método save del repositorio se haya llamado
        verify(odontologoRepository).save(any(Odontologo.class));
    }

    @Test
    void testGetOdontologo() throws ResourceNotFoundException {
        // Configurar comportamiento del mock
        Odontologo odontologo = new Odontologo();
        odontologo.setId(1L);
        odontologo.setNombre("John");
        odontologo.setApellido("Doe");
        odontologo.setMatricula(12345);
        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(odontologo));

        // Ejecutar el método a probar
        OdontologoDTO result = odontologoService.getOdontologo(1L);

        // Verificar el resultado
        assertEquals(1L, result.getId());
        assertEquals("John", result.getNombre());
        assertEquals("Doe", result.getApellido());
        assertEquals(12345, result.getMatricula());

        // Verificar que el método findById del repositorio se haya llamado
        verify(odontologoRepository).findById(1L);
    }

    @Test
    void testUpdateOdontologo() throws JsonMappingException, ResourceNotFoundException {
        // Crear objeto de prueba
        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setId(1L);
        odontologoDTO.setNombre("John");
        odontologoDTO.setApellido("Doe");
        odontologoDTO.setMatricula(12345);
        odontologoDTO.setTurnos(new ArrayList<>());

        // Configurar comportamiento del mock
        Odontologo odontologo = new Odontologo();
        odontologo.setId(1L);
        odontologo.setNombre("John");
        odontologo.setApellido("Doe");
        odontologo.setMatricula(12345);
        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(odontologo));
        when(odontologoRepository.save(any(Odontologo.class))).thenReturn(odontologo);

        // Ejecutar el método a probar
        OdontologoDTO result = odontologoService.updateOdontologo(1L, odontologoDTO);

        // Verificar el resultado
        assertEquals(1L, result.getId());
        assertEquals("John", result.getNombre());
        assertEquals("Doe", result.getApellido());
        assertEquals(12345, result.getMatricula());
        assertTrue(Objects.isNull(result.getTurnos()));

        // Verificar que el método findById y save del repositorio se hayan llamado
        verify(odontologoRepository).findById(1L);
        verify(odontologoRepository).save(any(Odontologo.class));
    }

    @Test
    void testGetAllOdontologo() {
        // Configurar comportamiento del mock
        Odontologo odontologo1 = new Odontologo();
        odontologo1.setId(1L);
        odontologo1.setNombre("John");
        odontologo1.setApellido("Doe");
        odontologo1.setMatricula(12345);
        Odontologo odontologo2 = new Odontologo();
        odontologo2.setId(2L);
        odontologo2.setNombre("Jane");
        odontologo2.setApellido("Smith");
        odontologo2.setMatricula(67890);
        List<Odontologo> odontologos = new ArrayList<>();
        odontologos.add(odontologo1);
        odontologos.add(odontologo2);
        when(odontologoRepository.findAll()).thenReturn(odontologos);

        // Ejecutar el método a probar
        List<OdontologoDTO> result = odontologoService.getAllOdontologo();

        // Verificar el resultado
        assertEquals(2, result.size());
        OdontologoDTO result1 = result.get(0);
        assertEquals(1L, result1.getId());
        assertEquals("John", result1.getNombre());
        assertEquals("Doe", result1.getApellido());
        assertEquals(12345, result1.getMatricula());
        OdontologoDTO result2 = result.get(1);
        assertEquals(2L, result2.getId());
        assertEquals("Jane", result2.getNombre());
        assertEquals("Smith", result2.getApellido());
        assertEquals(67890, result2.getMatricula());

        // Verificar que el método findAll del repositorio se haya llamado
        verify(odontologoRepository).findAll();
    }

    @Test
    void testDeleteOdontologo() {
        // Configurar comportamiento del mock
        Odontologo odontologo = new Odontologo();
        odontologo.setId(1L);
        odontologo.setNombre("John");
        odontologo.setApellido("Doe");
        odontologo.setMatricula(12345);
        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(odontologo));

        // Ejecutar el método a probar
        assertDoesNotThrow(() -> odontologoService.deleteOdontologo(1L));

        // Verificar que el método findById y delete del repositorio se hayan llamado
        verify(odontologoRepository).findById(1L);
        verify(odontologoRepository).delete(odontologo);
    }
}
