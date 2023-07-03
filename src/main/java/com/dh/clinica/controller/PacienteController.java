package com.dh.clinica.controller;

import com.dh.clinica.dto.PacienteDTO;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/")
    public ResponseEntity<PacienteDTO> createPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        try {
            PacienteDTO paciente = pacienteService.createPaciente(pacienteDTO);
            return new ResponseEntity<>(paciente, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> getPaciente(@PathVariable Long id) {
        try {
            PacienteDTO paciente = pacienteService.getPaciente(id);
            return ResponseEntity.ok().body(paciente);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<PacienteDTO>> getAllPaciente() {
        try {
            List<PacienteDTO> pacientes = pacienteService.getAllPacientes();
            return ResponseEntity.ok().body(pacientes);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> updatePaciente(@PathVariable Long id, @RequestBody PacienteDTO pacienteDTO) {
        try {
            PacienteDTO paciente = pacienteService.updatePaciente(id,pacienteDTO);
            return ResponseEntity.ok().body(paciente);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOdontologo(@PathVariable Long id) {
        try {
            pacienteService.deletePaciente(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
