package com.duxsoftware.challenge.repository;

import com.duxsoftware.challenge.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEquipoDAOHbn extends JpaRepository<Equipo, Long> {
    List<Equipo> findByNombreContainingIgnoreCase(String nombre);
}

