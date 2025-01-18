package com.duxsoftware.challenge.services.impl;

import com.duxsoftware.challenge.dto.request.EquipoRequest;
import com.duxsoftware.challenge.dto.response.EquipoResponse;
import com.duxsoftware.challenge.entity.Equipo;
import com.duxsoftware.challenge.repository.IEquipoDAOHbn;
import com.duxsoftware.challenge.exception.EquipoException;
import com.duxsoftware.challenge.services.IEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipoServiceImpl implements IEquipoService {

    @Autowired
    private IEquipoDAOHbn iEquipoDaoHbn;

    @Override
    public List<EquipoResponse> traerTodosLosEquipos() {
        return iEquipoDaoHbn.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EquipoResponse traerEquipoPorId(Long id) {
        Equipo equipo = iEquipoDaoHbn.findById(id)
                .orElseThrow(() -> new EquipoException("Equipo no encontrado", HttpStatus.NOT_FOUND.value()));
        return toResponse(equipo);
    }

    @Override
    public List<EquipoResponse> traerEquipoPorNombre(String nombre) {
        if (nombre.chars().anyMatch(Character::isDigit)) {
            throw new EquipoException("El parámetro nombre debe contener solo letras.", HttpStatus.BAD_REQUEST.value());
        }
        List<Equipo> equipos = iEquipoDaoHbn.findByNombreContainingIgnoreCase(nombre);
        if (equipos.isEmpty()) {
            throw new EquipoException("Equipo no encontrado", HttpStatus.NOT_FOUND.value());
        }
        return equipos.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public EquipoResponse crearEquipo(EquipoRequest request) {
        Equipo equipo = new Equipo();
        equipo.setNombre(request.getNombre());
        equipo.setPais(request.getPais());
        equipo.setLiga(request.getLiga());
        equipo = iEquipoDaoHbn.save(equipo);
        return toResponse(equipo);
    }

    @Override
    public EquipoResponse actualizarEquipoPorId(Long id, EquipoRequest request) {
        Equipo equipo = iEquipoDaoHbn.findById(id)
                .orElseThrow(() -> new EquipoException("Equipo no encontrado", HttpStatus.NOT_FOUND.value()));
        equipo.setNombre(request.getNombre());
        equipo.setPais(request.getPais());
        equipo.setLiga(request.getLiga());
        equipo = iEquipoDaoHbn.save(equipo);
        return toResponse(equipo);
    }

    @Override
    public void borrarEquipoPorId(Long id) {
        Equipo equipo = iEquipoDaoHbn.findById(id)
                .orElseThrow(() -> new EquipoException("Equipo no encontrado", HttpStatus.NOT_FOUND.value()));
        iEquipoDaoHbn.deleteById(id);
    }

    private EquipoResponse toResponse(Equipo equipo) {
        EquipoResponse response = new EquipoResponse();
        response.setId(equipo.getId());
        response.setNombre(equipo.getNombre());
        response.setPais(equipo.getPais());
        response.setLiga(equipo.getLiga());
        return response;
    }
}
