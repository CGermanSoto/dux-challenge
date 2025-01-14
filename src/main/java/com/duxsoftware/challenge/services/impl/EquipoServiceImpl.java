package com.duxsoftware.challenge.services.impl;

import com.duxsoftware.challenge.entity.Equipo;
import com.duxsoftware.challenge.exception.EquipoException;
import com.duxsoftware.challenge.repository.IEquipoDAOHbn;
import com.duxsoftware.challenge.services.IEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipoServiceImpl implements IEquipoService {

    @Autowired
    private IEquipoDAOHbn iEquipoDaoHbn;

    public List<Equipo> traerTodosLosEquipos(){
        return this.iEquipoDaoHbn.findAll();
    }

    public Equipo traerEquipoPorId(Long id){
        Equipo equipoEncontrado = this.iEquipoDaoHbn.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encuentra un equipo con id: " + id));
        return equipoEncontrado;
    }

    public List<Equipo> traerEquipoPorNombre(String nombre){
        List<Equipo> ListaEquipoEncontrado = this.iEquipoDaoHbn.findByNombreContainingIgnoreCase(nombre);
        return ListaEquipoEncontrado;
    }

    public Equipo crearEquipo(Equipo equipoNuevo){
        Equipo equipo = this.iEquipoDaoHbn.save(equipoNuevo);
        return equipo;
    }

    public Equipo actualizarEquipoPorId(Long id){
        Equipo equipoActualizado = this.iEquipoDaoHbn.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encuentra un equipo con id: " + id));

        return equipoActualizado;
    }

    public void borrarEquipoPorId(Long id){
        this.iEquipoDaoHbn.deleteById(id);
    }
}
