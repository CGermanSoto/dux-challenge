package com.duxsoftware.challenge.controller;

import com.duxsoftware.challenge.dto.request.EquipoRequest;
import com.duxsoftware.challenge.dto.response.EquipoResponse;
import com.duxsoftware.challenge.services.IEquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private IEquipoService iEquipoService;

    @Tag(name = "Obtener")
    @Operation(summary = "Obtener todos los equipos.",
            description = "Permite traer todos los equipos de la tabla Equipos.")
    @GetMapping
    public ResponseEntity<List<EquipoResponse>> traerTodosLosEquipos() {
        List<EquipoResponse> equipos = iEquipoService.traerTodosLosEquipos();
        return ResponseEntity.ok(equipos);
    }

    @Tag(name = "Obtener")
    @Operation(summary = "Obtener equipo por id.",
            description = "Permite obtener un equipo especifico de la tabla Equipos.")
    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponse> traerEquipoPorId(@PathVariable Long id) {
        EquipoResponse equipo = iEquipoService.traerEquipoPorId(id);
        return ResponseEntity.ok(equipo);
    }

    @Tag(name = "Obtener")
    @Operation(summary = "Obtener equipo por nombre.",
            description = "Permite obtener los equipos que coincidan con el nombre de la columna nombre de la tabla Equipos.")
    @GetMapping("/buscar")
    public ResponseEntity<List<EquipoResponse>> buscarEquipoPorNombre(@RequestParam String nombre) {
        List<EquipoResponse> equipos = iEquipoService.traerEquipoPorNombre(nombre);
        return ResponseEntity.ok(equipos);
    }

    @Tag(name = "Crear")
    @Operation(summary = "Crea un nuevo equipo."
            , description = "Permite crear un nuevo equipo en la tabla Equipos.")
    @PostMapping
    public ResponseEntity<EquipoResponse> crearEquipo(@RequestBody EquipoRequest request) {
        EquipoResponse equipoCreado = iEquipoService.crearEquipo(request);
        return ResponseEntity.status(201).body(equipoCreado);
    }

    @Tag(name = "Actualizar")
    @Operation(summary = "Actualiza un equipo."
            , description = "Permite actualizar un equipo especifico de la tabla Equipos.")
    @PutMapping("/{id}")
    public ResponseEntity<EquipoResponse> actualizarEquipoPorId(@PathVariable Long id, @RequestBody EquipoRequest request) {
        EquipoResponse equipoActualizado = iEquipoService.actualizarEquipoPorId(id, request);
        return ResponseEntity.ok(equipoActualizado);
    }

    @Tag(name = "Eliminar")
    @Operation(summary = "Elimina un equipo.",
    description = "Permite eliminar un equipo específico de la tabla Equipos.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarEquipoPorId(@PathVariable Long id) {
        iEquipoService.borrarEquipoPorId(id);
        return ResponseEntity.noContent().build();
    }
}
