package com.tinexlab.academia.model.repository;

import com.tinexlab.academia.model.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
}
