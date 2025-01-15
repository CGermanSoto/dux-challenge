package com.duxsoftware.challenge.services.impl;

import com.duxsoftware.challenge.entity.Equipo;
import com.duxsoftware.challenge.exception.EquipoException;
import com.duxsoftware.challenge.repository.IEquipoDAOHbn;
import com.duxsoftware.challenge.services.IEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import static com.duxsoftware.challenge.constants.EquipoConstantes.*;

@Service
public class EquipoServiceImpl implements IEquipoService {

    @Autowired
    private IEquipoDAOHbn iEquipoDaoHbn;

    public List<Equipo> traerTodosLosEquipos(){
        try{
            return this.iEquipoDaoHbn.findAll();
        } catch (RuntimeException e){
            throw new EquipoException(ERROR_SERVICIO + "traerTodosLosEquipos: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public Equipo traerEquipoPorId(Long id) {
        try {
            return iEquipoDaoHbn.findById(id)
                    .orElseThrow(() -> new EquipoException(EQUIPO_NO_ENCONTRADO, HttpStatus.NOT_FOUND.value()));
        } catch (EquipoException e) {
            throw e;
        } catch (Exception e) {
            throw new EquipoException(ERROR_SERVICIO + "traerEquipoPorId: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public List<Equipo> traerEquipoPorNombre(String nombre) {

        if (nombre.chars().anyMatch(Character::isDigit)) {
            throw new EquipoException("El parámetro nombre debe contener solo letras.", HttpStatus.BAD_REQUEST.value());
        }
        try {
            List<Equipo> listaEquipoEncontrado = this.iEquipoDaoHbn.findByNombreContainingIgnoreCase(nombre);
            if (listaEquipoEncontrado.isEmpty()) {
                throw new EquipoException(EQUIPO_NO_ENCONTRADO, HttpStatus.NOT_FOUND.value());
            }

            return listaEquipoEncontrado;
        } catch (EquipoException e) {
            throw e;
        } catch (Exception e) {
            throw new EquipoException(ERROR_SERVICIO + "traerEquipoPorNombre: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    public Equipo crearEquipo(Equipo equipoNuevo){
        /**
         * Comentar las validaciones no obligatorias
         */
        if(equipoNuevo.getNombre() == null
                || equipoNuevo.getLiga() == null
                || equipoNuevo.getPais() == null){
            throw new EquipoException("La solicitud es inválida", HttpStatus.BAD_REQUEST.value());
        }


        Equipo equipo = equipoNuevo;
        try{
            this.iEquipoDaoHbn.save(equipo);
        } catch (Exception e){
            throw new EquipoException(ERROR_SERVICIO + "crearEquipo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return equipo;
    }

    public Equipo actualizarEquipoPorId(Long id, Equipo equipoActualizado) {
        try {
            Equipo equipo = this.iEquipoDaoHbn.findById(id)
                    .orElseThrow(() -> new EquipoException(EQUIPO_NO_ENCONTRADO, HttpStatus.NOT_FOUND.value()));
            equipo.setLiga(equipoActualizado.getLiga());
            equipo.setNombre(equipoActualizado.getNombre());
            equipo.setPais(equipoActualizado.getPais());
            this.iEquipoDaoHbn.save(equipo);
            return equipo;
        } catch (EquipoException e) {
            throw e;
        } catch (Exception e) {
            throw new EquipoException(ERROR_SERVICIO + "actualizarEquipoPorId: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    public void borrarEquipoPorId(Long id){
        Equipo equipoABorrar = this.iEquipoDaoHbn.findById(id)
                .orElseThrow(() -> new EquipoException(EQUIPO_NO_ENCONTRADO, HttpStatus.NOT_FOUND.value()));

        try {
            this.iEquipoDaoHbn.deleteById(id);
        } catch (Exception e){
            throw new EquipoException(ERROR_SERVICIO + "borrarEquipoPorId: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
