package com.duxsoftware.challenge.controller;

import com.duxsoftware.challenge.dto.request.EquipoRequest;
import com.duxsoftware.challenge.dto.response.EquipoResponse;
import com.duxsoftware.challenge.services.IEquipoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private IEquipoService iEquipoService;

    @Operation(summary = "Obtener todos los equipos.")
    @GetMapping
    public ResponseEntity<List<EquipoResponse>> traerTodosLosEquipos() {
        List<EquipoResponse> equipos = iEquipoService.traerTodosLosEquipos();
        return ResponseEntity.ok(equipos);
    }

    @Operation(summary = "Obtener un equipo por id.")
    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponse> traerEquipoPorId(@PathVariable Long id) {
        EquipoResponse equipo = iEquipoService.traerEquipoPorId(id);
        return ResponseEntity.ok(equipo);
    }

    @Operation(summary = "Obtener todos los equipos que coincidan con el nombre.")
    @GetMapping("/buscar")
    public ResponseEntity<List<EquipoResponse>> buscarEquipoPorNombre(@RequestParam String nombre) {
        List<EquipoResponse> equipos = iEquipoService.traerEquipoPorNombre(nombre);
        return ResponseEntity.ok(equipos);
    }

    @Operation(summary = "Crea un nuevo equipo en la tabla.")
    @PostMapping
    public ResponseEntity<EquipoResponse> crearEquipo(@RequestBody EquipoRequest request) {
        EquipoResponse equipoCreado = iEquipoService.crearEquipo(request);
        return ResponseEntity.status(201).body(equipoCreado);
    }

    @Operation(summary = "Actualiza el equipo con los valores del requestBody.")
    @PutMapping("/{id}")
    public ResponseEntity<EquipoResponse> actualizarEquipoPorId(@PathVariable Long id, @RequestBody EquipoRequest request) {
        EquipoResponse equipoActualizado = iEquipoService.actualizarEquipoPorId(id, request);
        return ResponseEntity.ok(equipoActualizado);
    }

    @Operation(summary = "Elimina el equipo coincidiente con el id pasado por parámetro.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarEquipoPorId(@PathVariable Long id) {
        iEquipoService.borrarEquipoPorId(id);
        return ResponseEntity.noContent().build();
    }
}
