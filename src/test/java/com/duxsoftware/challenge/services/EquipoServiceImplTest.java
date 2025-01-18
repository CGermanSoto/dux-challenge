package com.duxsoftware.challenge.services;

import com.duxsoftware.challenge.dto.request.EquipoRequest;
import com.duxsoftware.challenge.dto.response.EquipoResponse;
import com.duxsoftware.challenge.entity.Equipo;
import com.duxsoftware.challenge.exception.EquipoException;
import com.duxsoftware.challenge.repository.IEquipoDAOHbn;
import com.duxsoftware.challenge.services.impl.EquipoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class EquipoServiceImplTest {

    @Mock
    private IEquipoDAOHbn iEquipoDaoHbn;

    @InjectMocks
    private EquipoServiceImpl equipoService;

    @Test
    void testTraerEquipoPorId_EquipoNoEncontrado() {
        Long id = 1L;

        Mockito.when(iEquipoDaoHbn.findById(id)).thenReturn(Optional.empty());

        EquipoException exception = Assertions.assertThrows(EquipoException.class, () -> {
            equipoService.traerEquipoPorId(id);
        });

        Assertions.assertEquals("Equipo no encontrado", exception.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), exception.getCodigo());
    }

    @Test
    void testTraerEquipoPorId_EquipoEncontrado() {
        Equipo equipoTest = new Equipo();
        equipoTest.setId(1L);
        equipoTest.setNombre("CASLA");
        equipoTest.setPais("Argentina");
        equipoTest.setLiga("Primera División");

        Mockito.when(iEquipoDaoHbn.findById(1L)).thenReturn(Optional.of(equipoTest));

        EquipoResponse response = equipoService.traerEquipoPorId(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("CASLA", response.getNombre());
    }

    @Test
    void testTraerTodosLosEquipos() {
        Equipo equipo1 = new Equipo();
        equipo1.setId(1L);
        equipo1.setNombre("CASLA");
        equipo1.setPais("Argentina");
        equipo1.setLiga("Primera División");

        Equipo equipo2 = new Equipo();
        equipo2.setId(2L);
        equipo2.setNombre("River Plate");
        equipo2.setPais("Argentina");
        equipo2.setLiga("Primera División");

        List<Equipo> equiposSimulados = Arrays.asList(equipo1, equipo2);

        Mockito.when(iEquipoDaoHbn.findAll()).thenReturn(equiposSimulados);

        List<EquipoResponse> todosLosEquipos = equipoService.traerTodosLosEquipos();

        Assertions.assertNotNull(todosLosEquipos);
        Assertions.assertEquals(2, todosLosEquipos.size());
        Assertions.assertEquals("CASLA", todosLosEquipos.get(0).getNombre());
        Assertions.assertEquals("River Plate", todosLosEquipos.get(1).getNombre());
    }

    @Test
    void traerEquipoPorNombre_EquipoEncontrado() {
        Equipo equipoTestA = new Equipo();
        equipoTestA.setId(1L);
        equipoTestA.setNombre("CASLA");
        equipoTestA.setPais("Argentina");
        equipoTestA.setLiga("Primera División");

        List<Equipo> equiposSimulados = Collections.singletonList(equipoTestA);

        Mockito.when(iEquipoDaoHbn.findByNombreContainingIgnoreCase("CASLA")).thenReturn(equiposSimulados);

        List<EquipoResponse> resultado = equipoService.traerEquipoPorNombre("CASLA");

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("CASLA", resultado.get(0).getNombre());
    }

    @Test
    void traerEquipoPorNombre_EquipoNoEncontrado() {
        Mockito.when(iEquipoDaoHbn.findByNombreContainingIgnoreCase("Test Nombre"))
                .thenReturn(Collections.emptyList());

        EquipoException exception = Assertions.assertThrows(EquipoException.class, () -> {
            equipoService.traerEquipoPorNombre("Test Nombre");
        });

        Assertions.assertEquals("Equipo no encontrado", exception.getMessage());
    }

    @Test
    void testCrearEquipo() {
        EquipoRequest request = new EquipoRequest();
        request.setNombre("CASLA");
        request.setPais("Argentina");
        request.setLiga("Primera División");

        Equipo equipoCreado = new Equipo();
        equipoCreado.setId(1L);
        equipoCreado.setNombre("CASLA");
        equipoCreado.setPais("Argentina");
        equipoCreado.setLiga("Primera División");

        Mockito.when(iEquipoDaoHbn.save(Mockito.any(Equipo.class))).thenReturn(equipoCreado);

        EquipoResponse response = equipoService.crearEquipo(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("CASLA", response.getNombre());
    }

    @Test
    void testActualizarEquipo() {
        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(1L);
        equipoExistente.setNombre("Old Name");
        equipoExistente.setPais("Old Country");
        equipoExistente.setLiga("Old League");

        Equipo equipoActualizado = new Equipo();
        equipoActualizado.setId(1L);
        equipoActualizado.setNombre("New Name");
        equipoActualizado.setPais("New Country");
        equipoActualizado.setLiga("New League");

        Mockito.when(iEquipoDaoHbn.findById(1L)).thenReturn(Optional.of(equipoExistente));
        Mockito.when(iEquipoDaoHbn.save(Mockito.any(Equipo.class))).thenReturn(equipoActualizado);

        EquipoRequest request = new EquipoRequest();
        request.setNombre("New Name");
        request.setPais("New Country");
        request.setLiga("New League");

        EquipoResponse response = equipoService.actualizarEquipoPorId(1L, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("New Name", response.getNombre());
    }
}
