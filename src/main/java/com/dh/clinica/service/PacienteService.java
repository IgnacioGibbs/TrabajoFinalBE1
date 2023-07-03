package com.dh.clinica.service;

import com.dh.clinica.dto.DomicilioDTO;
import com.dh.clinica.dto.PacienteDTO;
import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.DomicilioRepository;
import com.dh.clinica.repository.PacienteRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private DomicilioRepository domicilioRepository;

    private static final Logger log = LogManager.getLogger(PacienteService.class);

    private ObjectMapper mapper = new ObjectMapper();

    public PacienteDTO createPaciente(PacienteDTO pacienteDTO) {
        try {
            DomicilioDTO domicilio = this.domicilioService.createDomicilio(pacienteDTO.getDomicilio());
            pacienteDTO.setDomicilio(domicilio);
            Paciente paciente = mapper.convertValue(pacienteDTO, Paciente.class);
            PacienteDTO pacienteDto = mapper.convertValue(this.pacienteRepository.save(paciente), PacienteDTO.class);
            log.info("Se crea paciente con los siguientes valores: " + paciente);
            return pacienteDto;
        }catch (RuntimeException e){
            log.error(e.toString());
            throw e;
        }
    }

    public PacienteDTO getPaciente(Long id) throws ResourceNotFoundException {
        return mapper.convertValue(this.pacienteRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Paciente no encontrado: " + id) ) , PacienteDTO.class);
    }

    public List<PacienteDTO> getAllPacientes() {
        try {
            List<Paciente> pacientes = pacienteRepository.findAll();
            List<PacienteDTO> pacienteDTOS = new ArrayList<>();

            for (Paciente paciente : pacientes) {
                PacienteDTO pacienteDTO = mapper.convertValue(paciente, PacienteDTO.class);
                pacienteDTOS.add(pacienteDTO);
            }

            return pacienteDTOS;
        }
        catch (RuntimeException e){
            log.error(e.toString());
            throw e;
        }
    }

    public PacienteDTO updatePaciente(Long id, PacienteDTO pacienteDTO) throws JsonMappingException, ResourceNotFoundException {
        Paciente paciente = this.pacienteRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Paciente no encontrado: " + id));

        try {

            paciente.setNombre(pacienteDTO.getNombre());
            paciente.setApellido(pacienteDTO.getApellido());
            paciente.setDni(pacienteDTO.getDni());
            paciente.setFechaIngreso(pacienteDTO.getFechaIngreso());
            this.domicilioService.updateDomicilio(paciente.getDomicilio().getId(),pacienteDTO.getDomicilio());

            PacienteDTO pacienteDto = mapper.convertValue(this.pacienteRepository.save(paciente), PacienteDTO.class);
            log.info("Se actualiza paciente con los siguientes valores: " + paciente);
            return pacienteDto;
        }
        catch (RuntimeException | JsonMappingException e){
            log.error(e.toString());
            throw e;
        }
    }

    public void deletePaciente(Long id) throws ResourceNotFoundException {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Paciente not found, id " + id));
        try {
            log.info("Se intenta eliminar paciente: " + paciente);
            pacienteRepository.delete(paciente);
            this.domicilioService.deleteDomicilio(paciente.getDomicilio().getId());
            log.info("Se elimino paciente con id: " + id);
        } catch (RuntimeException e) {
            log.error(e.toString());
            throw e;
        }
    }
}
