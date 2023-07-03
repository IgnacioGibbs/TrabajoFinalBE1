package com.dh.clinica.service;

import com.dh.clinica.dto.OdontologoDTO;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.OdontologoRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    private static final Logger log = LogManager.getLogger(OdontologoService.class);

    private final ObjectMapper mapper = new ObjectMapper();

    public OdontologoDTO createOdontologo(OdontologoDTO odontologoDTO) {
        try {
            Odontologo odontologo = mapper.convertValue(odontologoDTO, Odontologo.class);
            OdontologoDTO odontologoDto =  mapper.convertValue(this.odontologoRepository.save(odontologo), OdontologoDTO.class);
            log.info("Se crea Odontologo con los siguientes valores: " + odontologoDto);
            return odontologoDto;
        }catch (RuntimeException e){
            log.error(e.toString());
            throw e;
        }
    }

    public OdontologoDTO getOdontologo(Long id) throws ResourceNotFoundException {
        return mapper.convertValue(this.odontologoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Odontologo no encontrado: " + id)), OdontologoDTO.class);

    }

    public OdontologoDTO updateOdontologo(Long id,OdontologoDTO odontologoDTO) throws ResourceNotFoundException {
         Odontologo odontologo = this.odontologoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Odonotologo no encontrado: " + id));
        try {
            odontologo.setApellido(odontologoDTO.getApellido());
            odontologo.setNombre(odontologoDTO.getNombre());
            odontologo.setMatricula(odontologoDTO.getMatricula());

            OdontologoDTO odontologoDto = mapper.convertValue(this.odontologoRepository.save(odontologo), OdontologoDTO.class);
            log.info("Se actualiza Odonotologo con los siguientes valores: " + odontologoDto);
            return  odontologoDto;
        }
        catch (RuntimeException e){
            log.error(e.toString());
            throw e;
        }
    }

    public List<OdontologoDTO> getAllOdontologo() {
        try {
            List<Odontologo> odontologos = odontologoRepository.findAll();
            List<OdontologoDTO> odontologoDTOs = new ArrayList<>();

            for (Odontologo odontologo : odontologos) {
                OdontologoDTO odontologoDTO = mapper.convertValue(odontologo, OdontologoDTO.class);
                odontologoDTOs.add(odontologoDTO);
            }

            return odontologoDTOs;
        }
        catch (RuntimeException e){
            log.error(e.toString());
            throw e;
        }
    }

    public void deleteOdontologo(Long id) throws ResourceNotFoundException {
        Odontologo odontologo = odontologoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Odontologo not found, id " + id));
        try {
            log.info("Se intenta eliminar Odontologo: " + odontologo);
            odontologoRepository.delete(odontologo);
            log.info("Se elimino Odontologo con id: " + id);
        } catch (RuntimeException e) {
            log.error(e.toString());
            throw e;
        }
    }

    public void deleteAll() {
        try {
            odontologoRepository.deleteAll();
            log.info("Se eliminaron todos los Odontologos");
        } catch (RuntimeException e) {
            log.error(e.toString());
            throw e;
        }
    }
}
