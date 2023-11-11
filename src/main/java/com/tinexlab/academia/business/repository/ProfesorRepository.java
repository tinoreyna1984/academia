package com.tinexlab.academia.business.repository;

import com.tinexlab.academia.business.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
}
