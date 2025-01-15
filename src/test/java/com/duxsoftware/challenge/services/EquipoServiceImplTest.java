package com.duxsoftware.challenge.services;

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
        Long id = 1L;
        equipoTest.setId(id);
        equipoTest.setNombre("CASLA");
        equipoTest.setPais("Argentina");
        equipoTest.setLiga("Primera División");

        Mockito.when(iEquipoDaoHbn.findById(id)).thenReturn(Optional.of(equipoTest));

        Equipo equipo = equipoService.traerEquipoPorId(id);

        Assertions.assertNotNull(equipo);
        Assertions.assertEquals("CASLA", equipo.getNombre());
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

        List<Equipo> todosLosEquipos = equipoService.traerTodosLosEquipos();

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

        List<Equipo> resultado = equipoService.traerEquipoPorNombre("CASLA");

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
    void traerEquipoPorNombre_EquiposEncontrados() {
        Equipo equipoA = new Equipo();
        equipoA.setId(1L);
        equipoA.setNombre("CASLA");
        equipoA.setPais("Argentina");
        equipoA.setLiga("Primera División");

        Equipo equipoB = new Equipo();
        equipoB.setId(2L);
        equipoB.setNombre("CASLA Corrientes");
        equipoB.setPais("Argentina");
        equipoB.setLiga("Primera División");

        List<Equipo> equiposSimulados = Arrays.asList(equipoA, equipoB);
        Mockito.when(iEquipoDaoHbn.findByNombreContainingIgnoreCase("CASLA")).thenReturn(equiposSimulados);

        List<Equipo> equiposEncontrados = equipoService.traerEquipoPorNombre("CASLA");

        Assertions.assertNotNull(equiposEncontrados);
        Assertions.assertEquals(2, equiposEncontrados.size());
        Assertions.assertTrue(equiposEncontrados.contains(equipoA));
        Assertions.assertTrue(equiposEncontrados.contains(equipoB));
    }



}

