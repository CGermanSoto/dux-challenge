package com.duxsoftware.challenge.services;

import com.duxsoftware.challenge.dto.request.EquipoRequest;
import com.duxsoftware.challenge.dto.response.EquipoResponse;

import java.util.List;

public interface IEquipoService {

    List<EquipoResponse> traerTodosLosEquipos();

    EquipoResponse traerEquipoPorId(Long id);

    List<EquipoResponse> traerEquipoPorNombre(String nombre);

    EquipoResponse crearEquipo(EquipoRequest request);

    EquipoResponse actualizarEquipoPorId(Long id, EquipoRequest request);

    void borrarEquipoPorId(Long id);
}
