package com.duxsoftware.challenge.controller;

import com.duxsoftware.challenge.entity.Equipo;
import com.duxsoftware.challenge.exception.EquipoException;
import com.duxsoftware.challenge.services.IEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private IEquipoService iEquipoService;

    @GetMapping
    private List<Equipo> traerTodosLosEquipos() {
        return this.iEquipoService.traerTodosLosEquipos();
    }

    @GetMapping("/{id}")
    private Equipo traerEquipoPorId(@PathVariable Long id) {
        return this.iEquipoService.traerEquipoPorId(id);
    }

    @GetMapping("/buscar")
    private List<Equipo> buscarEquipoPorNombre(@RequestParam String nombre) {
        return this.iEquipoService.traerEquipoPorNombre(nombre);
    }

    @PostMapping
    public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo equipoNuevo) {
        Equipo equipoCreado = this.iEquipoService.crearEquipo(equipoNuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoCreado);
    }


    @PutMapping("/{id}")
    private Equipo actualizarEquipoPorId(@PathVariable Long id, @RequestBody Equipo equipoActualizado) {
        return this.iEquipoService.actualizarEquipoPorId(id, equipoActualizado);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> borrarEquipoPorId(@PathVariable Long id) {
        this.iEquipoService.borrarEquipoPorId(id);
        return ResponseEntity.noContent().build();
    }

}
