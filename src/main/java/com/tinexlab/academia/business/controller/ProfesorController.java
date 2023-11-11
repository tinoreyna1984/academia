package com.tinexlab.academia.business.controller;

import com.tinexlab.academia.business.entity.Profesor;
import com.tinexlab.academia.business.dto.ProfesorRequest;
import com.tinexlab.academia.business.repository.ProfesorRepository;
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
public class ProfesorController {

    @Autowired
    private ProfesorRepository profesorRepository;


    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/profesores")
    public ResponseEntity<Object> listarProfesores(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            // Si se proporcionan los parámetros de paginación, devuelve una lista paginada
            Pageable pageable = PageRequest.of(page, size);
            Page<Profesor> pageResult = profesorRepository.findAll(pageable);
            return ResponseEntity.ok(pageResult);
        } else {
            // Si no se proporcionan los parámetros de paginación, devuelve una lista completa
            List<Profesor> profesores = profesorRepository.findAll();
            return ResponseEntity.ok(profesores);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/profesores/{id}")
    public ResponseEntity<?> buscarProfesor(@PathVariable Long id){
        Profesor profesor = null;
        Map<String, Object> response = new HashMap<>();

        try {
            profesor = profesorRepository.findById(id).get();
        }catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Profesor>(profesor, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("/profesores")
    public ResponseEntity<?> guardarProfesor(@Valid @RequestBody ProfesorRequest profesorRequest, BindingResult result){
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

        Profesor profesorNuevo = new Profesor();
        profesorNuevo.setNombreProfesor(profesorRequest.getNombreProfesor());
        profesorNuevo.setApellidosProfesor(profesorRequest.getApellidosProfesor());
        profesorNuevo.setCorreo(profesorRequest.getCorreo());

        try {
            profesorNuevo = profesorRepository.save(profesorNuevo);
        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El profesor ha sido creado con éxito");
        response.put("profesor", profesorNuevo);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/profesores/{id}")
    public ResponseEntity<?> editarProfesor(@Valid @RequestBody ProfesorRequest profesorRequest, BindingResult result, @PathVariable Long id){
        Profesor profesorActual = profesorRepository.findById(id).get();
        Profesor profesorEditado = null;
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
            profesorActual.setNombreProfesor(profesorRequest.getNombreProfesor());
            profesorActual.setApellidosProfesor(profesorRequest.getApellidosProfesor());
            profesorActual.setCorreo(profesorRequest.getCorreo());
            profesorEditado = profesorRepository.save(profesorActual);
        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el update en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El profesor ha sido editado con éxito");
        response.put("profesor", profesorEditado);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/profesores/{id}")
    public ResponseEntity<?> borrarProfesor(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        try {
            profesorRepository.deleteById(id);
        }catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el delete en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El profesor ha sido borrado con éxito");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }


}
