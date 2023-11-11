package com.tinexlab.academia.business.controller;

import com.tinexlab.academia.business.dto.AulaRequest;
import com.tinexlab.academia.business.entity.Aula;
import com.tinexlab.academia.business.entity.Materia;
import com.tinexlab.academia.business.entity.Profesor;
import com.tinexlab.academia.business.repository.AulaRepository;
import com.tinexlab.academia.business.repository.MateriaRepository;
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
public class AulaController {

    @Autowired
    private AulaRepository aulaRepository;
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private MateriaRepository materiaRepository;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/aulas")
    public ResponseEntity<Object> listarAulas(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            // Si se proporcionan los parámetros de paginación, devuelve una lista paginada
            Pageable pageable = PageRequest.of(page, size);
            Page<Aula> pageResult = aulaRepository.findAll(pageable);
            return ResponseEntity.ok(pageResult);
        } else {
            // Si no se proporcionan los parámetros de paginación, devuelve una lista completa
            List<Aula> aulas = aulaRepository.findAll();
            return ResponseEntity.ok(aulas);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/aulas/{id}")
    public ResponseEntity<?> buscarAula(@PathVariable Long id){
        Aula aula = null;
        Map<String, Object> response = new HashMap<>();

        try {
            aula = aulaRepository.findById(id).get();
        }catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Aula>(aula, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("/aulas")
    public ResponseEntity<?> guardarAula(@Valid @RequestBody AulaRequest aulaRequest, BindingResult result){
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

        Profesor profesor = profesorRepository.findById(aulaRequest.getProfesorId()).get();
        Materia materia = materiaRepository.findById(aulaRequest.getMateriaId()).get();
        if (profesor == null) {
            response.put("mensaje", "Profesor no encontrado con el ID: " + aulaRequest.getProfesorId());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        if (materia == null) {
            response.put("mensaje", "Materia no encontrada con el ID: " + aulaRequest.getMateriaId());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        Aula aulaNueva = new Aula();
        aulaNueva.setCodAula(aulaRequest.getCodAula());
        aulaNueva.setFechaHoraRegistro(aulaRequest.getFechaHoraRegistro());
        aulaNueva.setTema(aulaRequest.getTema());
        aulaNueva.setProfesor(profesor);
        aulaNueva.setMateria(materia);

        try {
            aulaNueva = aulaRepository.save(aulaNueva);
        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El aula ha sido creado con éxito");
        response.put("aula", aulaNueva);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/aulas/{id}")
    public ResponseEntity<?> editarAula(@Valid @RequestBody AulaRequest aulaRequest, BindingResult result, @PathVariable Long id){
        Aula aulaActual = aulaRepository.findById(id).get();
        Aula aulaEditada = null;
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

        Profesor profesor = profesorRepository.findById(aulaRequest.getProfesorId()).get();
        Materia materia = materiaRepository.findById(aulaRequest.getMateriaId()).get();
        if (profesor == null) {
            response.put("mensaje", "Profesor no encontrado con el ID: " + aulaRequest.getProfesorId());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        if (materia == null) {
            response.put("mensaje", "Materia no encontrada con el ID: " + aulaRequest.getMateriaId());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            aulaActual.setCodAula(aulaRequest.getCodAula());
            aulaActual.setFechaHoraRegistro(aulaRequest.getFechaHoraRegistro());
            aulaActual.setTema(aulaRequest.getTema());
            aulaActual.setProfesor(profesor);
            aulaActual.setMateria(materia);
            aulaEditada = aulaRepository.save(aulaActual);
        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el update en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El aula ha sido editado con éxito");
        response.put("aula", aulaEditada);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/aulas/{id}")
    public ResponseEntity<?> borrarAula(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        try {
            aulaRepository.deleteById(id);
        }catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el delete en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El aula ha sido eliminado con éxito");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
    }

}
