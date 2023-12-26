package com.tinexlab.academia.controller;

import com.tinexlab.academia.model.dto.request.ProfesorRequest;
import com.tinexlab.academia.service.ProfesorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/profesores")
    public ResponseEntity<Object> listarProfesores(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return profesorService.getProfesores(page, size);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/profesores/{id}")
    public ResponseEntity<?> buscarProfesor(@PathVariable Long id){
        return profesorService.getProfesor(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("/profesores")
    public ResponseEntity<?> guardarProfesor(@Valid @RequestBody ProfesorRequest profesorRequest, BindingResult result){
        return profesorService.saveProfesor(profesorRequest, result);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/profesores/{id}")
    public ResponseEntity<?> editarProfesor(@Valid @RequestBody ProfesorRequest profesorRequest,
                                            BindingResult result,
                                            @PathVariable Long id){
        return profesorService.updateProfesor(profesorRequest, result, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/profesores/{id}")
    public ResponseEntity<?> borrarProfesor(@PathVariable Long id){
        return profesorService.deleteProfesor(id);
    }
}
