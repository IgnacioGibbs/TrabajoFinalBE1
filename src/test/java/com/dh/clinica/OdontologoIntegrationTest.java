package com.dh.clinica;

import com.dh.clinica.dto.OdontologoDTO;
import com.dh.clinica.service.OdontologoService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Spy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class OdontologoIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OdontologoService odontologoService;

    @Spy
    private ObjectMapper mapper;

    @Test
    public void TestCreateOdontologo() throws Exception {
        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setNombre("Juan");
        odontologoDTO.setApellido("Ramirez");
        odontologoDTO.setMatricula(123);

        mockMvc.perform(MockMvcRequestBuilders.post("/odontologo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(odontologoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Ramirez"))
                .andExpect(jsonPath("$.matricula").value(123));
    }

    @Test
    public void TestGetOdontologo() throws Exception {
        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setNombre("Juan");
        odontologoDTO.setApellido("Ramirez");
        odontologoDTO.setMatricula(123);

        mockMvc.perform(MockMvcRequestBuilders.post("/odontologo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(odontologoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Ramirez"))
                .andExpect(jsonPath("$.matricula").value(123));
    }

    @Test
    public void TestGetAllOdontologos() throws Exception {

        odontologoService.deleteAll();

        OdontologoDTO odontologoDTO1 = new OdontologoDTO();
        odontologoDTO1.setNombre("Juan");
        odontologoDTO1.setApellido("Ramirez");
        odontologoDTO1.setMatricula(123);

        OdontologoDTO odontologoDTO2 = new OdontologoDTO();
        odontologoDTO2.setNombre("Pedro");
        odontologoDTO2.setApellido("Gomez");
        odontologoDTO2.setMatricula(456);

        odontologoService.createOdontologo(odontologoDTO1);
        odontologoService.createOdontologo(odontologoDTO2);



        mockMvc.perform(MockMvcRequestBuilders.get("/odontologo/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].apellido").value("Ramirez"))
                .andExpect(jsonPath("$[0].matricula").value(123))
                .andExpect(jsonPath("$[1].id").exists())
                .andExpect(jsonPath("$[1].nombre").value("Pedro"))
                .andExpect(jsonPath("$[1].apellido").value("Gomez"))
                .andExpect(jsonPath("$[1].matricula").value(456));
    }

    @Test
    public void TestUpdateOdontologo() throws Exception {
        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setNombre("Juan");
        odontologoDTO.setApellido("Ramirez");
        odontologoDTO.setMatricula(123);

        odontologoDTO = odontologoService.createOdontologo(odontologoDTO);

        odontologoDTO.setNombre("Pedro");
        odontologoDTO.setApellido("Gomez");
        odontologoDTO.setMatricula(456);

        mockMvc.perform(MockMvcRequestBuilders.post("/odontologo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(odontologoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Pedro"))
                .andExpect(jsonPath("$.apellido").value("Gomez"))
                .andExpect(jsonPath("$.matricula").value(456));
    }

    @Test
    public void TestDeleteOdontologo() throws Exception {
        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setNombre("Juan");
        odontologoDTO.setApellido("Ramirez");
        odontologoDTO.setMatricula(123);

        odontologoDTO = odontologoService.createOdontologo(odontologoDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/odontologo/{id}", odontologoDTO.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/odontologo/{id}", odontologoDTO.getId()))
                .andExpect(status().isNotFound());
    }
}
