package com.tinexlab.academia.model.dto.request;

import com.tinexlab.academia.util.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;
}
