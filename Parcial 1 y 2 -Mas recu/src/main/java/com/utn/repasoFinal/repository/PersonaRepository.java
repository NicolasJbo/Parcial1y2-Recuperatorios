package com.utn.repasoFinal.repository;

import com.utn.repasoFinal.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
}
