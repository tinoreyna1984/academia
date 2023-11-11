package com.tinexlab.academia.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AulaRequest {
    private String codAula;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date fechaHoraRegistro;
    private String tema;
    private Long profesorId;
    private Long materiaId;
}
