package com.dh.clinica.controller;

import com.dh.clinica.dto.DomicilioDTO;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.service.DomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/domicilio")
public class DomicilioController {

    @Autowired
    private DomicilioService domicilioService;

    @PostMapping("/")
    public ResponseEntity<DomicilioDTO> createDomicilio(@Valid @RequestBody DomicilioDTO domicilioDTO) {
        try {
            DomicilioDTO domicilio = domicilioService.createDomicilio(domicilioDTO);
            return new ResponseEntity<>(domicilio, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomicilioDTO> getDomicilio(@PathVariable Long id) {
        try {
            DomicilioDTO domicilio = domicilioService.getDomicilio(id);
            return ResponseEntity.ok().body(domicilio);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<DomicilioDTO>> getAllDomicilio() {
        try {
            List<DomicilioDTO> odontologo = domicilioService.getAllDomicilio();
            return ResponseEntity.ok().body(odontologo);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<DomicilioDTO> updateDomicilio(@PathVariable Long id, @RequestBody DomicilioDTO domicilioDTO) {
        try {
            DomicilioDTO domicilio = domicilioService.updateDomicilio(id,domicilioDTO);
            return ResponseEntity.ok().body(domicilio);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomicilio(@PathVariable Long id) {
        try {
            domicilioService.deleteDomicilio(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
