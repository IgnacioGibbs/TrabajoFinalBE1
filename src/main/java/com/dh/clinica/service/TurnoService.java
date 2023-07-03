package com.dh.clinica.service;

import com.dh.clinica.dto.TurnoDTO;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.OdontologoRepository;
import com.dh.clinica.repository.PacienteRepository;
import com.dh.clinica.repository.TurnoRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    private static final Logger log = LogManager.getLogger(TurnoService.class);

    private ObjectMapper mapper = new ObjectMapper();

    public TurnoDTO createTurno(TurnoDTO turnoDTO) {
        try {
            Optional<Odontologo> odontologo = odontologoRepository.findById(turnoDTO.getOdontologo().getId());
            Optional<Paciente> paciente = pacienteRepository.findById(turnoDTO.getPaciente().getId());

            Turno turno = new Turno();
            turno.setFechaTurno(turnoDTO.getFechaTurno());
            turno.setOdontologo(odontologo.get());
            turno.setPaciente(paciente.get());

            turno = this.turnoRepository.save(turno);

            TurnoDTO turnoDto = mapper.convertValue(turno, TurnoDTO.class);
            log.info("Se crea turno con los siguientes valores: " + turnoDto);
            return turnoDto;
        }catch (RuntimeException e){
            log.error(e.toString());
            throw e;
        }
    }

    public TurnoDTO getTurno(Long id) throws ResourceNotFoundException {
        return mapper.convertValue(this.turnoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("turno no encontrado: " + id)), TurnoDTO.class);
    }

    public List<TurnoDTO> getAllTurno() {
        try {
            List<Turno> turnos = turnoRepository.findAll();
            List<TurnoDTO> turnoDTOS = new ArrayList<>();

            for (Turno turno : turnos) {
                turnoDTOS.add(this.mapper.convertValue(turno, TurnoDTO.class));
            }


            return turnoDTOS;
        }
        catch (RuntimeException e){
            log.error(e.toString());
            throw e;
        }
    }

    public TurnoDTO updateTurno(Long id, TurnoDTO turnoDTO) {
        try {
            Optional<Odontologo> odontologo = odontologoRepository.findById(turnoDTO.getOdontologo().getId());
            Optional<Paciente> paciente = pacienteRepository.findById(turnoDTO.getPaciente().getId());
            Optional<Turno> turno = this.turnoRepository.findById(turnoDTO.getId());

            turno.get().setFechaTurno(turnoDTO.getFechaTurno());
            turno.get().setOdontologo(odontologo.get());
            turno.get().setPaciente(paciente.get());

            this.turnoRepository.save(turno.get());

            TurnoDTO turnoDto = mapper.convertValue(turno, TurnoDTO.class);
            log.info("Se actualiza turno con los siguientes valores: " + turnoDto);
            return turnoDto;
        }
        catch (RuntimeException e){
            log.error(e.toString());
            throw e;
        }
    }

    public void deleteTurno(Long id) throws ResourceNotFoundException {
        try {
            turnoRepository.delete(this.turnoRepository.findById(id).orElseThrow(()->
                    new ResourceNotFoundException("turno not found, id " + id)));
            log.info("Se elimino turno con id: " + id);
        } catch (RuntimeException | ResourceNotFoundException e) {
            log.error(e.toString());
            throw e;
        }
    }
}
