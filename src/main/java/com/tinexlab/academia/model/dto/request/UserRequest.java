package com.tinexlab.academia.model.dto.request;

import com.tinexlab.academia.util.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String password;
    @Pattern(regexp = "\\w[a-z0-9_.]+@[a-z0-9_.]+.[a-z]{2,3}.[a-z]{2,3}", message = "no presenta un correo válido")
    private String email;
    @Column(name = "nombre_usuario")
    private String name;
    @Column(name = "apellidos_usuario")
    private String lastName;
    @Column(name = "rol")
    @ColumnDefault("'USER'")
    @Enumerated(EnumType.STRING)
    private Role role;
}
