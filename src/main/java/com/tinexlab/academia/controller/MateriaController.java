package com.tinexlab.academia.controller;

import com.tinexlab.academia.model.dto.request.MateriaRequest;
import com.tinexlab.academia.service.MateriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/materias")
    public ResponseEntity<Object> listarMaterias(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return materiaService.getMaterias(page, size);
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR','ROLE_USER')")
    @GetMapping("/materias/{id}")
    public ResponseEntity<?> buscarMateria(@PathVariable Long id){
        return materiaService.getMateria(id);
    }


    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("/materias")
    public ResponseEntity<?> guardarMateria(@Valid @RequestBody MateriaRequest materiaRequest, BindingResult result){
        return materiaService.saveMateria(materiaRequest, result);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/materias/{id}")
    public ResponseEntity<?> editarMateria(@Valid @RequestBody MateriaRequest materiaRequest, BindingResult result, @PathVariable Long id){
        return materiaService.updateMateria(materiaRequest, result, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/materias/{id}")
    public ResponseEntity<?> borrarMateria(@PathVariable Long id){
        return materiaService.deleteMateria(id);
    }
}
