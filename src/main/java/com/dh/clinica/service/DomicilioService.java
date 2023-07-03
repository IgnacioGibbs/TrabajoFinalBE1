package com.dh.clinica.service;

import com.dh.clinica.dto.DomicilioDTO;
import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.exceptions.ResourceNotFoundException;
import com.dh.clinica.repository.DomicilioRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DomicilioService {

    @Autowired
    private DomicilioRepository domicilioRepository;

    private static final Logger log = LogManager.getLogger(DomicilioService.class);

    private final ObjectMapper mapper = new ObjectMapper();

    public DomicilioDTO createDomicilio(DomicilioDTO domicilioDTO) {
        try {
            Domicilio domicilio = this.domicilioRepository.save(mapper.convertValue(domicilioDTO, Domicilio.class));
            DomicilioDTO domicilioDto = mapper.convertValue(domicilio, DomicilioDTO.class);
            log.info("Se crea Domicilio con los siguientes valores: " + domicilioDto);
            return domicilioDto;
        }catch (RuntimeException e){
            log.error(e.toString());
            throw e;
        }
    }

    public DomicilioDTO getDomicilio(Long id) throws ResourceNotFoundException {
        return mapper.convertValue(this.domicilioRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Domicilio no encontrado: " + id)), DomicilioDTO.class);
    }

    public List<DomicilioDTO> getAllDomicilio() {
        try {
            List<Domicilio> domicilios = domicilioRepository.findAll();
            List<DomicilioDTO> domicilioDTOS = new ArrayList<>();

            for (Domicilio domicilio : domicilios) {
                DomicilioDTO domicilioDTO = mapper.convertValue(domicilio, DomicilioDTO.class);
                domicilioDTOS.add(domicilioDTO);
            }

            return domicilioDTOS;
        }
        catch (RuntimeException e){
            log.error(e.toString());
            throw e;
        }
    }

    public DomicilioDTO updateDomicilio(Long id, DomicilioDTO domicilioDTO) throws JsonMappingException, ResourceNotFoundException {
        Domicilio domicilio = this.domicilioRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Domicilio no encontrado: " + id));
        try {
            domicilio.setCalle(domicilioDTO.getCalle());
            domicilio.setNumero(domicilioDTO.getNumero());
            domicilio.setLocalidad(domicilioDTO.getLocalidad());
            domicilio.setProvincia(domicilioDTO.getProvincia());
            DomicilioDTO domicilioDto = mapper.convertValue(this.domicilioRepository.save(domicilio), DomicilioDTO.class);
            log.info("Se crea Domicilio con los siguientes valores: " + domicilioDto);
            return domicilioDto;
        }
        catch (RuntimeException e){
            log.error(e.toString());
            throw e;
        }
    }

    public void deleteDomicilio(Long id) throws ResourceNotFoundException {
        Domicilio domicilio = domicilioRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Domicilio not found, id " + id));
        try {
            log.info("Se intenta eliminar Domicilio: " + domicilio);
            domicilioRepository.delete(domicilio);
            log.info("Se elimino Domicilio con id: " + id);
        } catch (RuntimeException e) {
            log.error(e.toString());
            throw e;
        }
    }

    public void deleteAll() throws ResourceNotFoundException {
        try {
            log.info("Se intenta eliminar todos los Domicilios");
            domicilioRepository.deleteAll();
            log.info("Se eliminaron todos los Domicilios");
        } catch (RuntimeException e) {
            log.error(e.toString());
            throw e;
        }
    }
}
