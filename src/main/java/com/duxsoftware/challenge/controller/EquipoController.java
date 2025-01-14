package com.duxsoftware.challenge.controller;

import com.duxsoftware.challenge.entity.Equipo;
import com.duxsoftware.challenge.exception.EquipoException;
import com.duxsoftware.challenge.services.IEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private IEquipoService iEquipoService;

    @GetMapping
    private List<Equipo> traerTodosLosEquipos() {
        try {
            return this.iEquipoService.traerTodosLosEquipos();
        } catch (Exception e) {
            throw new EquipoException("Error al obtener todos los equipos.", 500);
        }
    }

    @GetMapping("/{id}")
    private Equipo traerEquipoPorId(@PathVariable Long id) {
        try {
            return this.iEquipoService.traerEquipoPorId(id);
        } catch (Exception e) {
            throw new EquipoException("Equipo con id: " + id + " no encontrado.", 404);
        }
    }

    @GetMapping("/buscar")
    private List<Equipo> buscarEquipoPorNombre(@RequestParam String nombre) {
        try {
            List<Equipo> listaEquipoPorNombre = this.iEquipoService.traerEquipoPorNombre(nombre);
            if(listaEquipoPorNombre.isEmpty())
                throw new EquipoException("No se encuentra equipo con nombre: " + nombre, 400);
            return listaEquipoPorNombre;
        } catch (Exception e) {
            throw new EquipoException("Error al consultar equipo por nombre.", 400);
        }
    }

    @PostMapping
    private Equipo crearEquipo(@RequestBody Equipo nuevoEquipo) {
        try {
            return this.iEquipoService.crearEquipo(nuevoEquipo);
        } catch (Exception e) {
            throw new EquipoException("La solicitud es inválida.", 400);
        }
    }

    @PutMapping("/{id}")
    private Equipo actualizarEquipoPorId(@PathVariable Long id, @RequestBody Equipo equipoActualizado) {
        try {
            return this.iEquipoService.actualizarEquipoPorId(id);
        } catch (Exception e) {
            throw new EquipoException("Error al actualizar el equipo con ID: " + id, 400);
        }
    }

    @DeleteMapping("/{id}")
    private void borrarEquipoPorId(@PathVariable Long id) {
        try {
            this.iEquipoService.borrarEquipoPorId(id);
        } catch (Exception e) {
            throw new EquipoException("Error al eliminar el equipo con ID: " + id, 400);
        }
    }
}
