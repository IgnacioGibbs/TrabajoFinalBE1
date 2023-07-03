package com.dh.clinica;

import com.dh.clinica.dto.DomicilioDTO;
import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.DomicilioRepository;
import com.dh.clinica.service.DomicilioService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class DomicilioServiceTest {
    @Mock
    private DomicilioRepository domicilioRepository;

    @InjectMocks
    private DomicilioService domicilioService;

    @Spy
    private ObjectMapper mapper = new ObjectMapper();

    DomicilioServiceTest() throws JsonProcessingException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDomicilio() {
        // Crear objeto de prueba
        DomicilioDTO domicilioDTO = new DomicilioDTO();
        domicilioDTO.setCalle("Calle 1");
        domicilioDTO.setNumero(123);
        domicilioDTO.setLocalidad("Ciudad");
        domicilioDTO.setProvincia("Provincia");

//		// Configurar comportamiento del mock
        Domicilio domicilioGuardado = mapper.convertValue(domicilioDTO, Domicilio.class);
        domicilioGuardado.setId(1L);
        when(domicilioRepository.save(any(Domicilio.class))).thenReturn(domicilioGuardado);

        // Ejecutar el método a probar
        DomicilioDTO result = domicilioService.createDomicilio(domicilioDTO);

        // Verificar el resultado
        assertEquals(1L, result.getId());
        assertEquals("Calle 1", result.getCalle());
        assertEquals(123, result.getNumero());
        assertEquals("Ciudad", result.getLocalidad());
        assertEquals("Provincia", result.getProvincia());

        // Verificar que el método save del repositorio se haya llamado
        verify(domicilioRepository).save(any(Domicilio.class));
    }

    @Test
    void testGetDomicilio() throws ResourceNotFoundException {
        // Configurar comportamiento del mock
        Domicilio domicilioExistente = new Domicilio();
        domicilioExistente.setId(1L);
        domicilioExistente.setCalle("Calle 1");
        domicilioExistente.setNumero(123);
        domicilioExistente.setLocalidad("Ciudad");
        domicilioExistente.setProvincia("Provincia");
        when(domicilioRepository.findById(1L)).thenReturn(Optional.of(domicilioExistente));

        // Ejecutar el método a probar
        DomicilioDTO result = domicilioService.getDomicilio(1L);

        // Verificar el resultado
        assertEquals(1L, result.getId());
        assertEquals("Calle 1", result.getCalle());
        assertEquals(123, result.getNumero());
        assertEquals("Ciudad", result.getLocalidad());
        assertEquals("Provincia", result.getProvincia());

        // Verificar que el método findById del repositorio se haya llamado
        verify(domicilioRepository).findById(1L);
    }

    @Test
    void testGetAllDomicilio() {
        // Configurar comportamiento del mock
        List<Domicilio> domicilios = new ArrayList<>();
        Domicilio domicilio1 = new Domicilio();
        domicilio1.setId(1L);
        domicilio1.setCalle("Calle 1");
        domicilio1.setNumero(123);
        domicilio1.setLocalidad("Ciudad");
        domicilio1.setProvincia("Provincia");
        domicilios.add(domicilio1);
        Domicilio domicilio2 = new Domicilio();
        domicilio2.setId(2L);
        domicilio2.setCalle("Calle 2");
        domicilio2.setNumero(456);
        domicilio2.setLocalidad("Otra Ciudad");
        domicilio2.setProvincia("Otra Provincia");
        domicilios.add(domicilio2);
        when(domicilioRepository.findAll()).thenReturn(domicilios);

        // Ejecutar el método a probar
        List<DomicilioDTO> result = domicilioService.getAllDomicilio();

        // Verificar el resultado
        assertEquals(2, result.size());
        DomicilioDTO result1 = result.get(0);
        assertEquals(1L, result1.getId());
        assertEquals("Calle 1", result1.getCalle());
        assertEquals(123, result1.getNumero());
        assertEquals("Ciudad", result1.getLocalidad());
        assertEquals("Provincia", result1.getProvincia());
        DomicilioDTO result2 = result.get(1);
        assertEquals(2L, result2.getId());
        assertEquals("Calle 2", result2.getCalle());
        assertEquals(456, result2.getNumero());
        assertEquals("Otra Ciudad", result2.getLocalidad());
        assertEquals("Otra Provincia", result2.getProvincia());

        // Verificar que el método findAll del repositorio se haya llamado
        verify(domicilioRepository).findAll();
    }

    @Test
    void testUpdateDomicilio() throws JsonMappingException, ResourceNotFoundException {
        // Crear objeto de prueba
        DomicilioDTO domicilioDTO = new DomicilioDTO();
        domicilioDTO.setId(1L);
        domicilioDTO.setCalle("Calle Nueva");
        domicilioDTO.setNumero(999);
        domicilioDTO.setLocalidad("Nueva Ciudad");
        domicilioDTO.setProvincia("Nueva Provincia");

        // Configurar comportamiento del mock
        Domicilio domicilioExistente = new Domicilio();
        domicilioExistente.setId(1L);
        domicilioExistente.setCalle("Calle Antigua");
        domicilioExistente.setNumero(123);
        domicilioExistente.setLocalidad("Ciudad Antigua");
        domicilioExistente.setProvincia("Provincia Antigua");
        when(domicilioRepository.findById(1L)).thenReturn(Optional.of(domicilioExistente));
        when(domicilioRepository.save(any(Domicilio.class))).thenReturn(domicilioExistente);

        // Ejecutar el método a probar
        DomicilioDTO result = domicilioService.updateDomicilio(1L, domicilioDTO);

        // Verificar el resultado
        assertEquals(1L, result.getId());
        assertEquals("Calle Nueva", result.getCalle());
        assertEquals(999, result.getNumero());
        assertEquals("Nueva Ciudad", result.getLocalidad());
        assertEquals("Nueva Provincia", result.getProvincia());

        // Verificar que el método findById y save del repositorio se hayan llamado
        verify(domicilioRepository).findById(1L);
        verify(domicilioRepository).save(any(Domicilio.class));
    }

    @Test
    void testDeleteDomicilio() throws ResourceNotFoundException {
        // Configurar comportamiento del mock
        Domicilio domicilioExistente = new Domicilio();
        domicilioExistente.setId(1L);
        domicilioExistente.setCalle("Calle 1");
        domicilioExistente.setNumero(123);
        domicilioExistente.setLocalidad("Ciudad");
        domicilioExistente.setProvincia("Provincia");
        when(domicilioRepository.findById(1L)).thenReturn(Optional.of(domicilioExistente));

        // Ejecutar el método a probar
        domicilioService.deleteDomicilio(1L);

        // Verificar que el método findById y delete del repositorio se hayan llamado
        verify(domicilioRepository).findById(1L);
        verify(domicilioRepository).delete(any(Domicilio.class));
    }
}
