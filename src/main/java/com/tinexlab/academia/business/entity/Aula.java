package com.tinexlab.academia.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "aulas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula")
    private Long id;
    private String codAula;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date fechaHoraRegistro;
    private String tema;

    @ManyToOne
    @JoinColumn(name="id_profesor", referencedColumnName = "id_profesor")
    @JsonManagedReference
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name="id_materia", referencedColumnName = "id_materia")
    @JsonManagedReference
    private Materia materia;
}
