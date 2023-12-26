package com.tinexlab.academia.model.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfesorRequest {
    private String nombreProfesor;
    private String apellidosProfesor;
    @Pattern(regexp = "\\w[a-z0-9_.]+@[a-z0-9_.]+.[a-z]{2,3}.[a-z]{2,3}", message = "no presenta un correo válido")
    private String correo;
}
