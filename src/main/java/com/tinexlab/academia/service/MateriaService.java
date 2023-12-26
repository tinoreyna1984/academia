package com.tinexlab.academia.service;

import com.tinexlab.academia.model.dto.request.MateriaRequest;
import com.tinexlab.academia.model.entity.Materia;
import com.tinexlab.academia.model.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MateriaService {
    @Autowired
    private MateriaRepository materiaRepository;

    public ResponseEntity<Object> getMaterias(Integer page, Integer size){
        if (page != null && size != null) {
            // Si se proporcionan los parámetros de paginación, devuelve una lista paginada
            Pageable pageable = PageRequest.of(page, size);
            Page<Materia> pageResult = materiaRepository.findAll(pageable);
            return ResponseEntity.ok(pageResult);
        } else {
            // Si no se proporcionan los parámetros de paginación, devuelve una lista completa
            List<Materia> materias = materiaRepository.findAll();
            return ResponseEntity.ok(materias);
        }
    }

    public ResponseEntity<?> getMateria(Long id){
        Materia materia = null;
        Map<String, Object> response = new HashMap<>();

        try {
            materia = materiaRepository.findById(id).get();
        }catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Materia>(materia, HttpStatus.OK);
    }

    public ResponseEntity<?> saveMateria(MateriaRequest materiaRequest, BindingResult result){
        Map<String, Object> response = new HashMap<>();

        // proceso de validación
        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        Materia materiaNueva = new Materia();
        materiaNueva.setNombreMateria(materiaRequest.getNombreMateria());
        materiaNueva.setDescMateria(materiaRequest.getDescMateria());

        try {
            materiaNueva = materiaRepository.save(materiaNueva);
        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La materia ha sido creada con éxito");
        response.put("materia", materiaNueva);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateMateria(MateriaRequest materiaRequest, BindingResult result, Long id){
        Materia materiaActual = materiaRepository.findById(id).get();
        Materia materiaEditada = null;
        Map<String, Object> response = new HashMap<>();

        // proceso de validación
        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            materiaActual.setNombreMateria(materiaRequest.getNombreMateria());
            materiaActual.setDescMateria(materiaRequest.getDescMateria());
            materiaEditada = materiaRepository.save(materiaActual);
        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el update en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La materia ha sido editada con éxito");
        response.put("materia", materiaEditada);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> deleteMateria(Long id){
        Map<String, Object> response = new HashMap<>();

        try {
            materiaRepository.deleteById(id);
        }catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el delete en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La materia ha sido eliminada con éxito");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }
}
