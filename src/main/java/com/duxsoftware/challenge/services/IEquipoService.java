package com.duxsoftware.challenge.services;

import com.duxsoftware.challenge.entity.Equipo;
import java.util.List;

public interface IEquipoService {

    public List<Equipo> traerTodosLosEquipos();

    public Equipo traerEquipoPorId(Long id);

    public List<Equipo> traerEquipoPorNombre(String nombre);

    public Equipo crearEquipo(Equipo equipoNuevo);

    public Equipo actualizarEquipoPorId(Long id, Equipo equipoActualizado);

    public void borrarEquipoPorId(Long id);
}
