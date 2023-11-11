package com.tinexlab.academia.business.controller;

import com.tinexlab.academia.business.dto.MateriaRequest;
import com.tinexlab.academia.business.entity.Materia;
import com.tinexlab.academia.business.repository.MateriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MateriaController {
    @Autowired
    private MateriaRepository materiaRepository;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/materias")
    public ResponseEntity<Object> listarMaterias(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
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


    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/materias/{id}")
    public ResponseEntity<?> buscarMateria(@PathVariable Long id){
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


    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("/materias")
    public ResponseEntity<?> guardarMateria(@Valid @RequestBody MateriaRequest materiaRequest, BindingResult result){
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

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/materias/{id}")
    public ResponseEntity<?> editarMateria(@Valid @RequestBody MateriaRequest materiaRequest, BindingResult result, @PathVariable Long id){
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

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/materias/{id}")
    public ResponseEntity<?> borrarMateria(@PathVariable Long id){
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
