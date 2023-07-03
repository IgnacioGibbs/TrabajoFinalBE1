package com.dh.clinica;

import com.dh.clinica.service.DomicilioService;
import com.dh.clinica.dto.DomicilioDTO;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.runner.RunWith;
import org.junit.Test;

import org.mockito.Spy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class DomicilioIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DomicilioService domicilioService;

    @Spy
    private ObjectMapper mapper;

    @Test
    public void testCreateDomicilio() throws Exception {
        DomicilioDTO domicilioDTO = new DomicilioDTO();
        domicilioDTO.setCalle("Calle 1");
        domicilioDTO.setNumero(123);
        domicilioDTO.setLocalidad("Localidad 1");
        domicilioDTO.setProvincia("Provincia 1");

        mockMvc.perform(MockMvcRequestBuilders.post("/domicilio/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(domicilioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.calle").value("Calle 1"))
                .andExpect(jsonPath("$.numero").value(123))
                .andExpect(jsonPath("$.localidad").value("Localidad 1"))
                .andExpect(jsonPath("$.provincia").value("Provincia 1"));
    }

    @Test
    public void testGetDomicilio() throws Exception {
        DomicilioDTO domicilioDTO = new DomicilioDTO();
        domicilioDTO.setCalle("Calle 1");
        domicilioDTO.setNumero(123);
        domicilioDTO.setLocalidad("Localidad 1");
        domicilioDTO.setProvincia("Provincia 1");

        // Guardar el domicilio de prueba en la base de datos
        domicilioDTO = domicilioService.createDomicilio(domicilioDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/domicilio/{id}", domicilioDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(domicilioDTO.getId().intValue()))
                .andExpect(jsonPath("$.calle").value("Calle 1"))
                .andExpect(jsonPath("$.numero").value(123))
                .andExpect(jsonPath("$.localidad").value("Localidad 1"))
                .andExpect(jsonPath("$.provincia").value("Provincia 1"));
    }

    @Test
    public void testGetAllDomicilio() throws Exception {

        domicilioService.deleteAll();

        DomicilioDTO domicilioDTO1 = new DomicilioDTO();
        domicilioDTO1.setCalle("Calle 1");
        domicilioDTO1.setNumero(123);
        domicilioDTO1.setLocalidad("Localidad 1");
        domicilioDTO1.setProvincia("Provincia 1");

        DomicilioDTO domicilioDTO2 = new DomicilioDTO();
        domicilioDTO2.setCalle("Calle 2");
        domicilioDTO2.setNumero(456);
        domicilioDTO2.setLocalidad("Localidad 2");
        domicilioDTO2.setProvincia("Provincia 2");

        domicilioDTO1 = domicilioService.createDomicilio(domicilioDTO1);
        domicilioDTO2 = domicilioService.createDomicilio(domicilioDTO2);

        mockMvc.perform(MockMvcRequestBuilders.get("/domicilio/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(domicilioDTO1.getId().intValue()))
                .andExpect(jsonPath("$[0].calle").value("Calle 1"))
                .andExpect(jsonPath("$[0].numero").value(123))
                .andExpect(jsonPath("$[0].localidad").value("Localidad 1"))
                .andExpect(jsonPath("$[0].provincia").value("Provincia 1"))
                .andExpect(jsonPath("$[1].id").value(domicilioDTO2.getId().intValue()))
                .andExpect(jsonPath("$[1].calle").value("Calle 2"))
                .andExpect(jsonPath("$[1].numero").value(456))
                .andExpect(jsonPath("$[1].localidad").value("Localidad 2"))
                .andExpect(jsonPath("$[1].provincia").value("Provincia 2"));
    }

    @Test
    public void testUpdateDomicilio() throws Exception {
        DomicilioDTO domicilioDTO = new DomicilioDTO();
        domicilioDTO.setCalle("Calle 1");
        domicilioDTO.setNumero(123);
        domicilioDTO.setLocalidad("Localidad 1");
        domicilioDTO.setProvincia("Provincia 1");

        // Guardar el domicilio de prueba en la base de datos
        domicilioDTO = domicilioService.createDomicilio(domicilioDTO);

        domicilioDTO.setCalle("Calle 2");
        domicilioDTO.setNumero(456);
        domicilioDTO.setLocalidad("Localidad 2");
        domicilioDTO.setProvincia("Provincia 2");

        mockMvc.perform(MockMvcRequestBuilders.put("/domicilio/{id}", domicilioDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(domicilioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(domicilioDTO.getId().intValue()))
                .andExpect(jsonPath("$.calle").value("Calle 2"))
                .andExpect(jsonPath("$.numero").value(456))
                .andExpect(jsonPath("$.localidad").value("Localidad 2"))
                .andExpect(jsonPath("$.provincia").value("Provincia 2"));
    }

    @Test
    public void testDeleteDomicilio() throws Exception {
        DomicilioDTO domicilioDTO = new DomicilioDTO();
        domicilioDTO.setCalle("Calle 1");
        domicilioDTO.setNumero(123);
        domicilioDTO.setLocalidad("Localidad 1");
        domicilioDTO.setProvincia("Provincia 1");

        // Guardar el domicilio de prueba en la base de datos
        domicilioDTO = domicilioService.createDomicilio(domicilioDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/domicilio/{id}", domicilioDTO.getId()))
                .andExpect(status().isOk());

        // Verificar que el domicilio se haya eliminado
        mockMvc.perform(MockMvcRequestBuilders.get("/domicilio/{id}", domicilioDTO.getId()))
                .andExpect(status().isNotFound());
    }
}
