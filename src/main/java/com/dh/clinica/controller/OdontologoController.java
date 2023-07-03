package com.dh.clinica.controller;

import com.dh.clinica.dto.OdontologoDTO;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;

    @PostMapping("/")
    public ResponseEntity<OdontologoDTO> createOdontologo(@Valid @RequestBody OdontologoDTO odontologoDTO) {
        try {
            OdontologoDTO odontologo= odontologoService.createOdontologo(odontologoDTO);
            return new ResponseEntity<>(odontologo, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDTO> getOdontologo(@PathVariable Long id) {
        try {
            OdontologoDTO odontologo = odontologoService.getOdontologo(id);
            return ResponseEntity.ok().body(odontologo);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<OdontologoDTO>> getAllOdontologo() {
        try {
            List<OdontologoDTO> odontologo = odontologoService.getAllOdontologo();
            return ResponseEntity.ok().body(odontologo);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<OdontologoDTO> updateOdontologo(@PathVariable Long id, @RequestBody OdontologoDTO odontologoDTO) {
        try {
            //mostrar por consola el id y el odontologoDTO
            System.out.println("id: " + id);
            System.out.println("nombre " + odontologoDTO.getNombre());
            System.out.println("apellido " + odontologoDTO.getApellido());
            System.out.println("matricula " + odontologoDTO.getMatricula());
            OdontologoDTO odontologo = odontologoService.updateOdontologo(id,odontologoDTO);
            return ResponseEntity.ok().body(odontologo);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOdontologo(@PathVariable Long id) {
        try {
            odontologoService.deleteOdontologo(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
