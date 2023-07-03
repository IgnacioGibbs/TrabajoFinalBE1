package com.dh.clinica.controller;

import com.dh.clinica.dto.TurnoDTO;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @PostMapping("/")
    public ResponseEntity<TurnoDTO> createTurno(@Valid @RequestBody TurnoDTO turnoDTO) {
        try {
            return new ResponseEntity<>(this.turnoService.createTurno(turnoDTO), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> getTurno(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(this.turnoService.getTurno(id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<TurnoDTO>> getAllTurno() {
        try {
            List<TurnoDTO> turnoDTOS = turnoService.getAllTurno();
            return ResponseEntity.ok().body(turnoDTOS);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<TurnoDTO> updateTurno(@PathVariable Long id, @RequestBody TurnoDTO turnoDTO) {
        try {

            return ResponseEntity.ok().body(this.turnoService.updateTurno(id,turnoDTO));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurno(@PathVariable Long id) {
        try {
            this.turnoService.deleteTurno(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
