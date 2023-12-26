package com.tinexlab.academia.controller;

import com.tinexlab.academia.model.dto.request.AulaRequest;
import com.tinexlab.academia.model.entity.Aula;
import com.tinexlab.academia.model.entity.Materia;
import com.tinexlab.academia.model.entity.Profesor;
import com.tinexlab.academia.model.repository.AulaRepository;
import com.tinexlab.academia.model.repository.MateriaRepository;
import com.tinexlab.academia.model.repository.ProfesorRepository;
import com.tinexlab.academia.service.AulaService;
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
    private AulaService aulaService;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/aulas")
    public ResponseEntity<Object> listarAulas(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return aulaService.getAulas(page, size);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/aulas/{id}")
    public ResponseEntity<?> buscarAula(@PathVariable Long id){
        return aulaService.getAula(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("/aulas")
    public ResponseEntity<?> guardarAula(@Valid @RequestBody AulaRequest aulaRequest, BindingResult result){
        return aulaService.saveAula(aulaRequest, result);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/aulas/{id}")
    public ResponseEntity<?> editarAula(@Valid @RequestBody AulaRequest aulaRequest, BindingResult result, @PathVariable Long id){
        return aulaService.updateAula(aulaRequest, result, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/aulas/{id}")
    public ResponseEntity<?> borrarAula(@PathVariable Long id){
        return aulaService.deleteAula(id);
    }

}
